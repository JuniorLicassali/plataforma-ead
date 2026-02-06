package com.plataforma.plataforma_ead;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

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

import com.cloudinary.Cloudinary;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroModuloIT {
	
	private static final int ID_INEXISTENTE = 100;
	private static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@MockitoBean
	private Cloudinary storageService;
	
	private Curso cursoExemplo;
	private Modulo moduloExemplo;
	
	private String jsonCorreto;
	private String jsonIncorreto;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cursos";

		jsonCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/modulos.json");
		
		jsonIncorreto = ResourceUtils.getContentFromResource(
				"/json/incorreto/modulos.json");
		
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornar200_QuandoListarModulos() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cursoId}/modulos")
	    .then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar200_QuandoBuscarModuloPorId() {

	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", moduloExemplo.getId())
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cursoId}/modulos/{moduloId}")
	    .then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarModulo() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .body(jsonCorreto) 
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post("/{cursoId}/modulos")
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar200_QuandoAtualizarModulo() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", moduloExemplo.getId())
	        .body(jsonCorreto)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .put("/{cursoId}/modulos/{moduloId}")
	    .then()
	        .statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar404_QuandoBuscarModuloInexistente() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("moduloId", ID_INEXISTENTE)
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cursoId}/modulos/{moduloId}")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value())
	    	.body("title", equalTo(RECURSO_NAO_ENCONTRADO));
	}
	
	private void prepararDados() {
		Curso curso = new Curso();
		curso.setNome("Spring Boot Expert");
	    curso.setDescricao("Um curso completo de Spring Boot");
	    curso.setPreco(new BigDecimal("200.0"));
	    curso.setAtivo(true);
	    
	    cursoExemplo = cursoRepository.save(curso);
	    
	    Modulo modulo = new Modulo();
	    modulo.setNome("Modulo 1");
	    modulo.setDescricao("5 aulas");
	    modulo.setCurso(cursoExemplo);
	    
	    cursoExemplo.getModulos().add(modulo);
	    
	    cursoExemplo = cursoRepository.save(cursoExemplo);
	    moduloExemplo = cursoExemplo.getModulos().get(0);
	}

}
