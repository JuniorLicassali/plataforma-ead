package com.plataforma.plataforma_ead.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.plataforma.plataforma_ead.api.dto.QuestionarioDTO;
import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.IdUsuarioAbrirQestionarioTesteInput;
import com.plataforma.plataforma_ead.api.dto.input.PerguntaInput;
import com.plataforma.plataforma_ead.api.dto.input.QuestionarioInput;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.PerguntaOpcao;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;
import com.plataforma.plataforma_ead.domain.service.CadastroQuestionarioService;

@RestController
@RequestMapping(path = "/cursos/{cursoId}/questionarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionarioController {
	
	@Autowired
	private CadastroQuestionarioService questionarioService;
	
	@Autowired
	private QuestionarioDTOAssembler questionarioDTOAssembler;
	
	@Autowired
	private QuestionarioInputDisassembler questionarioInputDisassembler;
	
	@GetMapping("/{questionarioId}")
	public QuestionarioDTO buscar(@PathVariable Long cursoId, @PathVariable Long questionarioId) {
		QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioService.buscarOuFalhar(cursoId));
		
		return questionarioDTO;
	}
	
//	@GetMapping
//    public QuestionarioDTO abrirQuestionario(@PathVariable Long cursoId) {
//
////        QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioService.buscarOuFalhar(cursoId));
//		QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioService.abrirQuestionario(cursoId));
//		
//        return questionarioDTO;
//    }
	
	
	@GetMapping
	public QuestionarioDTO abrirQuestionario(@PathVariable Long cursoId, @RequestBody IdUsuarioAbrirQestionarioTesteInput usuarioId) {

//      QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioService.buscarOuFalhar(cursoId));
		
		QuestionarioUsuario questionarioUsuario = questionarioService.iniciarQuestionario(cursoId, usuarioId.getUsuarioId());
		
		QuestionarioDTO questionarioDTO = questionarioDTOAssembler.toDTO(questionarioUsuario);
		
		
		
      return questionarioDTO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionarioDTO criarQuestionario(@PathVariable Long cursoId, @RequestBody QuestionarioInput questionarioInput) {
		Questionario questionario = questionarioInputDisassembler.toDomainObject(questionarioInput);
		
		questionario = questionarioService.criarQuestionario(cursoId, questionario);
		
		return questionarioDTOAssembler.toDTO(questionario);
	}
	
	@PostMapping("/{questionarioId}")
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionarioDTO adicionarPergunta(@PathVariable Long cursoId, @PathVariable Long questionarioId, @RequestBody PerguntaInput perguntaInput) {

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

	@PostMapping("/{questionarioId}/respostas")
	public List<RespostaDTO> enviarRespostas(@PathVariable Long cursoId, @PathVariable Long questionarioId, @RequestBody List<RespostaInput> respostasInput) throws Exception {
		
	    List<RespostaDTO> resultados = questionarioService.verificarRespostas(cursoId, questionarioId, respostasInput);

	    return resultados;
	}
	
}
