package com.plataforma.plataforma_ead;

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

import io.restassured.RestAssured;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroAulaIT {
	
	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@MockitoBean
	private Cloudinary storage;
	
	@Autowired
	private CursoRepository cursoRepository;

	private Curso cursoExemplo;
	private Modulo moduloExemplo;
	private Aula aulaExemplo;
	
	private String jsonCorreto;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cursos";
		
		jsonCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/aula.json");
		
		databaseCleaner.clearTables();
		prepararDados();
		
		mockCloudinary();
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarAulaCompleta() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", moduloExemplo.getId())
	        .multiPart("aulaInput", jsonCorreto, "application/json")
	        .multiPart("video", "aula.mp4", "conteudo-fake".getBytes(), "video/mp4")
	    .when()
	        .post("/{cursoId}/modulos/{moduloId}/aulas")
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test 
	public void deveRetornar400_QuandoCadastrarAulaSemVideo() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", moduloExemplo.getId())
	        .multiPart("aulaInput", jsonCorreto, "application/json")
	    .when()
	    	.post("/{cursoId}/modulos/{moduloId}/aulas")
	    .then()
	    	.statusCode(HttpStatus.BAD_REQUEST.value())
	    	.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	@Test
	public void deveRetornar204_QuandoExcluirAulaComSucesso() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", moduloExemplo.getId())
	        .pathParam("aulaId", aulaExemplo.getId())
	    .when()
	    	.delete("/{cursoId}/modulos/{moduloId}/aulas/{aulaId}")
	    .then()
	    	.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	
	
	private void prepararDados() {
	    
		Curso curso = new Curso();
		curso.setNome("Spring Boot Expert");
	    curso.setDescricao("Um curso completo de Spring Boot");
	    curso.setPreco(new BigDecimal("200.0"));
	    curso.setAtivo(true);
	    
	    Modulo modulo = new Modulo();
	    modulo.setNome("Inicial");
	    modulo.setDescricao("Bem vindo");
	    modulo.setCurso(curso);
	    modulo.setOrdem(1);
	    
	    Aula aula = prepararAula(modulo);
	    
	    modulo.getAulas().add(aula);
	    
	    curso.getModulos().add(modulo);
	    
	    cursoExemplo = cursoRepository.save(curso);
	    
	    moduloExemplo = cursoExemplo.getModulos().get(0);
	    aulaExemplo = moduloExemplo.getAulas().get(0);
	    
	}
	
	private void mockCloudinary() {
		Uploader uploaderMock = Mockito.mock(Uploader.class);

	    Mockito.when(storage.uploader()).thenReturn(uploaderMock);

	    Map<String, Object> resultadoFake = new HashMap<>();
	    resultadoFake.put("url", "https://res.cloudinary.com/teste/video-fake.mp4");

	    try {
	        Mockito.when(uploaderMock.upload(Mockito.any(), Mockito.anyMap()))
	               .thenReturn(resultadoFake);
	    } catch (IOException e) {
	        throw new RuntimeException("Erro ao configurar o mock", e);
	    }
	}
	
	private Aula prepararAula(Modulo modulo) {
		Aula aula = new Aula();
		aula.setTitulo("Instalando ferramentas");
		aula.setDescricao("Aula obrigatoria para prosseguir o curso");
		aula.setOrdem(1);
		aula.setModulo(modulo);
		
	    return aula;
	}

}
