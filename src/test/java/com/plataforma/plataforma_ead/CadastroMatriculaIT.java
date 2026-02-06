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
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroMatriculaIT {
	
	private static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
	private static final int ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@MockitoBean
	private Cloudinary storageService;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/matriculas";
		
		databaseCleaner.clearTables();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarMatriculas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoBuscarMatriculaComIdInexistente() {
		given()
			.pathParam("matriculaId", ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.post("/matriculas/{matriculaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("title", equalTo(RECURSO_NAO_ENCONTRADO));
	}
	

}
