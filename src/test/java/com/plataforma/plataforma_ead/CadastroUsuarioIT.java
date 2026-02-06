package com.plataforma.plataforma_ead;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import com.cloudinary.Cloudinary;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroUsuarioIT {
	
	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	private static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
	private static final int ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@MockitoBean
	private Cloudinary storageService;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	private Curso cursoExemplo;
	private Usuario usuarioExemplo;
	
	private String jsonCorreto;
	private String jsonSenha;
	private String jsonIncorreto;
	private String jsonCorretoMatricula;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/usuarios";

		jsonCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/usuario.json");
		
		jsonIncorreto = ResourceUtils.getContentFromResource(
				"/json/incorreto/usuario.json");
		
		jsonSenha = ResourceUtils.getContentFromResource(
				"/json/correto/usuario-atualizar-senha.json");
		
		jsonCorretoMatricula = ResourceUtils.getContentFromResource(
				"/json/correto/matricula.json");
		
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	
	@Test
	public void deveRetornarStatus200_QuandoListarUsuarios() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarUsuario() {
		given()
			.pathParam("usuarioId", 1)
			.accept(ContentType.JSON)
		.when()
			.get("/{usuarioId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
    public void deveRetornarStatus404_QuandoConsultarUsuarioInexistente() {
        given()
            .pathParam("usuarioId", ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
        	.get("/{usuarioId}")
        .then()
        	.statusCode(HttpStatus.NOT_FOUND.value())
            .body("title", equalTo(RECURSO_NAO_ENCONTRADO));
    }
    
    
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarUsuario() {
		given()
			.body(jsonCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
    public void deveRetornarStatus400_QuandoCadastrarUsuarioComDadosIncorretos() {
        given()
            .body(jsonIncorreto)
            .contentType(ContentType.JSON)
        .when()
        	.post()
        .then()
        	.statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
	
	@Test
	public void deveRetornarStatus201_QuandoMatricularUsuario() {
		given()
			.pathParam("usuarioId", cursoExemplo.getId())
			.body(jsonCorretoMatricula)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/{usuarioId}/matriculas")
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoMatricularUsuarioComIdInexistente() {
		given()
			.pathParam("usuarioId", ID_INEXISTENTE)
			.body(jsonCorretoMatricula)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/{usuarioId}/matriculas")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("title", equalTo(RECURSO_NAO_ENCONTRADO));
	}
	
	
	
	@Test
	public void deveRetornarStatus204_QuandoAlterarSenhaUsuario() {
		given()
			.pathParam("usuarioId", 1)
			.body(jsonSenha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("{usuarioId}/senha")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarFotoDoCurso() {
		given()
			.pathParam("usuarioId", usuarioExemplo.getId())
			.multiPart("arquivo", "foto-teste.jpg", "conteúdo-da-imagem".getBytes(), "image/jpeg")
			.accept(ContentType.JSON)
		.when()
			.put("/{usuarioId}/foto")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	
	private void prepararDados() {
		Usuario usuario = new Usuario();
	    usuario.setNome("Sebastian");
	    usuario.setEmail("sebastian@teste.com");
	    usuario.setSenha("123");
	    usuarioExemplo = usuarioRepository.save(usuario);
	    
		Curso curso = new Curso();
		curso.setNome("Spring Boot Expert");
	    curso.setDescricao("Um curso completo de Spring Boot");
	    curso.setPreco(new BigDecimal("200.0"));
	    curso.setAtivo(true);
	    cursoExemplo = cursoRepository.save(curso);
	}

}
