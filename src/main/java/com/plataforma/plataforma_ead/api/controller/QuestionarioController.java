package com.plataforma.plataforma_ead.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.QuestionarioDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.QuestionarioInputDisassembler;
import com.plataforma.plataforma_ead.api.assembler.QuestionarioUsuarioDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.QuestionarioDTO;
import com.plataforma.plataforma_ead.api.dto.QuestionarioUsuarioDTO;
import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.IdUsuarioAbrirQestionarioTesteInput;
import com.plataforma.plataforma_ead.api.dto.input.PerguntaInput;
import com.plataforma.plataforma_ead.api.dto.input.QuestionarioInput;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;
import com.plataforma.plataforma_ead.api.openapi.controller.QuestionarioControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.core.security.PlataformaSecurity;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.PerguntaOpcao;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;
import com.plataforma.plataforma_ead.domain.service.CadastroQuestionarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/cursos/{cursoId}/questionarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuestionarioController implements QuestionarioControllerOpenApi {
	
	private final CadastroQuestionarioService questionarioService;
	private final QuestionarioDTOAssembler questionarioDTOAssembler;
	private final QuestionarioInputDisassembler questionarioInputDisassembler;
	private final QuestionarioUsuarioDTOAssembler questionarioUsuarioAssembler;
	private final PlataformaSecurity plataformaSecurity;
	
	@CheckSecurity.Questionario.PodeEditar
	@Override
	@GetMapping("/{questionarioId}")
	public QuestionarioDTO buscar(@PathVariable Long cursoId, @PathVariable Long questionarioId) {
		QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioService.buscarOuFalhar(cursoId));
		
		return questionarioDTO;
	}
	
	@CheckSecurity.Questionario.PodeConsultar
	@Override
	@GetMapping
	public QuestionarioUsuarioDTO iniciarQuestionario(@PathVariable Long cursoId, @RequestBody IdUsuarioAbrirQestionarioTesteInput usuarioId) {
		
		QuestionarioUsuario questionarioUsuario = questionarioService.iniciarQuestionario(cursoId, usuarioId.getUsuarioId());
		
		return questionarioUsuarioAssembler.toDTO(questionarioUsuario);
	}
	
	@CheckSecurity.Questionario.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionarioDTO criarQuestionario(@PathVariable Long cursoId, @RequestBody @Valid QuestionarioInput questionarioInput) {
		Questionario questionario = questionarioInputDisassembler.toDomainObject(questionarioInput);
		
		questionario = questionarioService.criarQuestionario(cursoId, questionario);
		
		return questionarioDTOAssembler.toDTO(questionario);
	}
	
	@CheckSecurity.Questionario.PodeEditar
	@Override
	@PostMapping("/{questionarioId}")
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionarioDTO adicionarPergunta(@PathVariable Long cursoId, @PathVariable Long questionarioId, @RequestBody @Valid PerguntaInput perguntaInput) {

		Pergunta pergunta = new Pergunta();
		pergunta.setEnunciado(perguntaInput.getEnunciado());
		    
		List<PerguntaOpcao> opcoes = perguntaInput.getOpcoes().stream()
				.map(opcaoInput -> {
		            PerguntaOpcao opcao = new PerguntaOpcao();
		            opcao.setTexto(opcaoInput.getTexto());
		            opcao.setIsCorreta(opcaoInput.getIsCorreta());
		            return opcao;
		        }).collect(Collectors.toList());
		    
		pergunta.setOpcoes(opcoes);
	
		Questionario questionario = questionarioService.buscarOuFalhar(questionarioId);
		pergunta.setQuestionario(questionario);
		questionario.getPerguntas().add(pergunta);

		questionarioService.adicionarPerguntaAoQuestionario(cursoId, pergunta);

		return questionarioDTOAssembler.toDTO(questionario);
		
	}

	@CheckSecurity.Questionario.PodeConsultar
	@Override
	@PostMapping("/{questionarioId}/respostas")
	@ResponseStatus(HttpStatus.CREATED)
	public List<RespostaDTO> enviarRespostas(@PathVariable Long cursoId, @PathVariable Long questionarioId, @RequestBody List<RespostaInput> respostasInput) throws Exception {
		
		Long usuarioId = plataformaSecurity.getUsuarioId();
	    List<RespostaDTO> resultados = questionarioService.verificarRespostas(cursoId, questionarioId, usuarioId, respostasInput);

	    return resultados;
	}
	
}
