package com.plataforma.plataforma_ead;

import static io.restassured.RestAssured.given;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cloudinary.Cloudinary;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.AsaasGatewayImpl;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasCobrancaResponse;
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class PagamentoIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@MockitoBean
	private AsaasGatewayImpl pagamentoIntegracao;
	
	@MockitoBean
	private Cloudinary storage;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	private String jsonCorreto;
	
	private Curso cursoExemplo;
	private Usuario usuarioExemplo;
	private Matricula matriculaExemplo;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/pagamentos";
		
		jsonCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/pagamento.json");
		
		databaseCleaner.clearTables();
		prepararDados();
		
		Mockito.when(pagamentoIntegracao.criarCobranca(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(prepararAsaas());
	}
	
	@Test
	public void deveRetornar201_QuandoProcessarPagamento() {

	    given()
	        .body(jsonCorreto)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	private void prepararDados() {
	    
		Curso curso = new Curso();
		curso.setNome("Spring Boot Expert");
	    curso.setDescricao("Um curso completo de Spring Boot");
	    curso.setPreco(new BigDecimal("200.0"));
	    curso.setAtivo(true);
	    
	    
	    cursoExemplo = cursoRepository.save(curso);
	    
	    Usuario usuario = new Usuario();
	    usuario.setNome("João Silva");
	    usuario.setEmail("joao@teste.com");
	    usuario.setSenha("123456");
	    usuario.setDataCadastro(OffsetDateTime.now());
	    usuarioExemplo = usuarioRepository.save(usuario);
	    
	    Matricula matricula = new Matricula();
	    matricula.setCurso(cursoExemplo);
	    matricula.setUsuario(usuarioExemplo);
	    matricula.setDataMatricula(OffsetDateTime.now());
	    matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_PENDENTE); 
	    matriculaExemplo = matriculaRepository.save(matricula);
	  
	    
	}

	private AsaasCobrancaResponse prepararAsaas() {
		AsaasCobrancaResponse asaas =  new AsaasCobrancaResponse();
		asaas.setId("AFC123");
		asaas.setStatus("AGUARDANDO");
		asaas.setInvoiceUrl("https://sandbox.asaas.com.br");
		return asaas;
	}


}
