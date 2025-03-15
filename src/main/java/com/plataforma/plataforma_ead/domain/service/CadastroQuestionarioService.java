package com.plataforma.plataforma_ead.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;
import com.plataforma.plataforma_ead.domain.exception.CursoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CadastroQuestionarioService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CadastroCursoService cursoService;

	@Transactional
	public Questionario criarOuObterQuestionario(Long cursoId) {

		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

		if (curso.getQuestionario() != null) {
			return curso.getQuestionario();
		}

		Questionario questionario = new Questionario();
		questionario.setCurso(curso);
		questionario.setAtivo(true);

		curso.setQuestionario(questionario);
		cursoRepository.save(curso);

		return questionario;
	}

	@Transactional
	public Questionario criarQuestionario(Long cursoId, Questionario questionario) {
		Curso curso = cursoService.buscarOuFalhar(cursoId);

		Questionario novoQuestionario = new Questionario();
		novoQuestionario.setDescricao(questionario.getDescricao());
		novoQuestionario.setCurso(curso);

		if (curso.getQuestionario() == null) {
			curso.setQuestionario(novoQuestionario);
		} else {
			curso.getQuestionario().setDescricao(novoQuestionario.getDescricao());
		}
//	    cursoRepository.flush();
		curso = cursoService.salvar(curso);

		return curso.getQuestionario();

	}

	@Transactional
	public Questionario adicionarPerguntaAoQuestionario(Long cursoId, Pergunta pergunta) {

		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

		Questionario questionario = curso.getQuestionario();

		if (questionario == null) {
			throw new IllegalStateException("O curso ainda não tem um questionário.");
		}

		pergunta.setQuestionario(questionario);

		pergunta.getOpcoes().forEach(opcao -> {
			opcao.setPergunta(pergunta);
		});

		questionario.getPerguntas().add(pergunta);

		cursoRepository.save(curso);

		return questionario;
	}

	public List<RespostaDTO> verificarRespostas(Long cursoId, Long questionarioId, List<RespostaInput> respostasInput)
			throws Exception {

		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

		Questionario questionario = curso.getQuestionario();
		if (questionario == null) {
			throw new QuestionarioNaoEncontradoException("O curso não tem um questionário.");
		}

		List<RespostaDTO> resultados = new ArrayList<>();

		for (RespostaInput respostaInput : respostasInput) {
			Pergunta pergunta = questionario.getPerguntas().stream()
					.filter(p -> p.getId().equals(respostaInput.getPerguntaId())).findFirst()
					.orElseThrow(() -> new Exception("Pergunta não encontrada"));

			boolean respostaCorreta = pergunta.verificarResposta(respostaInput.getResposta());

			RespostaDTO respostaDTO = new RespostaDTO();
			respostaDTO.setPerguntaId(respostaInput.getPerguntaId());
			respostaDTO.setResposta(respostaInput.getResposta());
			respostaDTO.setIsCorreta(respostaCorreta);

			resultados.add(respostaDTO);
		}

		return resultados;
	}

	public Questionario buscarOuFalhar(Long cursoId) {
		return cursoRepository.findQuestionarioById(cursoId)
				.orElseThrow(() -> new QuestionarioNaoEncontradoException(cursoId));
	}

}
