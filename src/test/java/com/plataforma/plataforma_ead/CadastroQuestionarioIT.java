package com.plataforma.plataforma_ead;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

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
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.PerguntaOpcao;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.QuestionarioRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;
import com.plataforma.plataforma_ead.util.DatabaseCleaner;
import com.plataforma.plataforma_ead.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroQuestionarioIT {
	
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
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private QuestionarioRepository questionarioRepository;

	private Curso cursoExemplo;
	private Questionario questionarioExemplo;
	private Usuario usuarioExemplo;
	
	private String jsonCorreto;
	private String jsonCorretoPerguntas;
	private String jsonCorretoRespostas;
	private String jsonIncorreto;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cursos";

		jsonCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/questionario-criar.json");
		
		jsonCorretoPerguntas = ResourceUtils.getContentFromResource(
				"/json/correto/questionario-adicionar-pergunta.json");
		
		jsonCorretoRespostas = ResourceUtils.getContentFromResource(
				"/json/correto/questionario-enviar-respostas.json");
		
		jsonIncorreto = ResourceUtils.getContentFromResource(
				"/json/incorreto/questionario-criar.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornar404_QuandoUsuarioNaoMatriculadoTentarIniciar() {
		
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .body("{ \"usuarioId\": " + ID_INEXISTENTE + " }")
	        .contentType(ContentType.JSON)
	    .when()
	        .get("/{cursoId}/questionarios")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value())
	    .body("title", equalTo(RECURSO_NAO_ENCONTRADO));
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarQuestionario() {
		given()
			.pathParam("cursoId", cursoExemplo.getId())
			.body(jsonCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/{cursoId}/questionarios")
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarPerguntasAoQuestionario() {
		criarQuestionario();
		given()
			.pathParam("cursoId", cursoExemplo.getId())
			.pathParam("questionarioId", questionarioExemplo.getId())
			.body(jsonCorretoPerguntas)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/{cursoId}/questionarios/{questionarioId}")
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar400_QuandoCadastrarQuestionarioComDadosInvalidos() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .body(jsonIncorreto)
	        .contentType(ContentType.JSON)
	    .when()
	        .post("/{cursoId}/questionarios")
	    .then()
	        .statusCode(HttpStatus.BAD_REQUEST.value())
	        .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	@Test
	public void deveRetornar404_QuandoEnviarRespostasParaQuestionarioInexistente() {
	    given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .pathParam("questionarioId", ID_INEXISTENTE)
	        .queryParam("usuarioId", usuarioExemplo.getId())
	        .body(jsonCorretoRespostas)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post("/{cursoId}/questionarios/{questionarioId}/respostas")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value())
	    	.body("title", equalTo(RECURSO_NAO_ENCONTRADO));
	}
	
	@Test
	public void deveRetornar201_QuandoEnviarRespostasCorretasDoQuestionarioAberto() {
		criarQuestionario();
		criarPerguntas();
		
		
		given()
	        .pathParam("cursoId", cursoExemplo.getId())
	        .body("{ \"usuarioId\": " + usuarioExemplo.getId() + " }")
	        .contentType(ContentType.JSON)
	    .when()
        	.get("/{cursoId}/questionarios")
        .then()
        	.statusCode(HttpStatus.OK.value());
		
		given()
			.pathParam("cursoId", cursoExemplo.getId())
			.pathParam("questionarioId", questionarioExemplo.getId())
			.queryParam("usuarioId", 1)
			.body(jsonCorretoRespostas)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/{cursoId}/questionarios/{questionarioId}/respostas")
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
	    usuario.setNome("João");
	    usuario.setEmail("joao@teste.com");
	    usuario.setSenha("123");
	    usuario.setDataCadastro(OffsetDateTime.now());
	    usuarioExemplo = usuarioRepository.save(usuario);
	    
	    Matricula matricula = new Matricula();
	    matricula.setDataMatricula(OffsetDateTime.now());
	    matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);
	    matricula.setCurso(cursoExemplo);
	    matricula.setUsuario(usuarioExemplo);
	    matriculaRepository.save(matricula);
	    
	}
	
	private void criarQuestionario() {
		Questionario questionario = new Questionario();
	    questionario.setDescricao("Prova de Lógica");
	    
	    cursoExemplo.setQuestionario(questionario);
	    
	    questionarioExemplo = questionarioRepository.save(questionario);
	    cursoExemplo = cursoRepository.save(cursoExemplo);
	}
	
	private void criarPerguntas() {
		Pergunta pergunta = new Pergunta();
		pergunta.setEnunciado("Qual a capital do Brasil?");
		
		PerguntaOpcao opcao1 = new PerguntaOpcao();
		opcao1.setPergunta(pergunta);
		opcao1.setTexto("Paris");
		opcao1.setIsCorreta(false);
		
		PerguntaOpcao opcao2 = new PerguntaOpcao();
		opcao2.setPergunta(pergunta);
		opcao2.setTexto("Londres");
		opcao2.setIsCorreta(false);
		
		PerguntaOpcao opcao3 = new PerguntaOpcao();
		opcao3.setPergunta(pergunta);
		opcao3.setTexto("Roma");
		opcao3.setIsCorreta(false);
		
		PerguntaOpcao opcao4 = new PerguntaOpcao();
		opcao4.setPergunta(pergunta);
		opcao4.setTexto("Brasília");
		opcao4.setIsCorreta(true);
		
		
		pergunta.setQuestionario(questionarioExemplo);
		
		pergunta.setOpcoes(Arrays.asList(opcao1, opcao2, opcao3, opcao4));
		
		
		questionarioExemplo.getPerguntas().add(pergunta);
		questionarioRepository.save(questionarioExemplo);
	}
	

}
