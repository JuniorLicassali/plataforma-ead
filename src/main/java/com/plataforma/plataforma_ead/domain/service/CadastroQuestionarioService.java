package com.plataforma.plataforma_ead.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;
import com.plataforma.plataforma_ead.domain.exception.CursoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioExpiradoException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioUsuarioDuplicadoException;
import com.plataforma.plataforma_ead.domain.exception.UsuarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class CadastroQuestionarioService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

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
			throw new NegocioException("O curso ainda não tem um questionário.");
		}

		pergunta.setQuestionario(questionario);

		pergunta.getOpcoes().forEach(opcao -> {
			opcao.setPergunta(pergunta);
		});

		questionario.getPerguntas().add(pergunta);

		cursoRepository.save(curso);

		return questionario;
	}
	
	public Questionario abrirQuestionario(Long cursoId) {
		Curso curso = cursoService.buscarOuFalhar(cursoId);
		
		Questionario questionario = curso.getQuestionario();
		
		OffsetDateTime horarioAtual = OffsetDateTime.now();
		
		if(questionario.getDataFechamento() != null && horarioAtual.isAfter(questionario.getDataFechamento())) {
			throw new QuestionarioExpiradoException("Tempo esgotado! Você não pode mais acessar este questionário.");
		}
		
		if(questionario.getDataAbertura() == null) {
			questionario.setDataAbertura(horarioAtual);
            questionario.setDataFechamento(horarioAtual.plusMinutes(60));
            cursoRepository.save(curso);
		}
		
		return questionario;
	}
	
	
	
	@Transactional
	public QuestionarioUsuario iniciarQuestionario(Long cursoId, Long usuarioId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(cursoId));
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Matricula matricula = Optional.ofNullable(usuarioRepository.findByCursoAndUsuario(curso, usuario))
        		.orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso e usuário"));
        
        QuestionarioUsuario questionarioUsuarioExistente = buscarQuestionarioExistente(curso, matricula);
        
        if(questionarioUsuarioExistente != null) {
        	if(isQuestionarioExpirado(questionarioUsuarioExistente)){
        		throw new QuestionarioExpiradoException("O questionário expirou.");
        	}
        	return questionarioUsuarioExistente;
        }
        
        QuestionarioUsuario novoQuestionario = criarNovoQuestionarioUsuario(curso, matricula);
        persistirQuestionarioUsuario(curso, novoQuestionario);
        
        return recuperarQuestionarioPersistido(curso, matricula);
    }

	private QuestionarioUsuario buscarQuestionarioExistente(Curso curso, Matricula matricula) {
		return curso.getQuestionario().getQuestionariosUsuarios().stream()
				.filter(q -> q.getMatricula().getId().equals(matricula.getId()))
				.findFirst()
				.orElse(null);
	}
	
	private QuestionarioUsuario criarNovoQuestionarioUsuario(Curso curso, Matricula matricula) {
		Questionario questionario = buscarOuFalhar(curso.getId());

        QuestionarioUsuario questionarioUsuario = new QuestionarioUsuario();
        questionarioUsuario.setMatricula(matricula);
        questionarioUsuario.setQuestionario(questionario);
        questionarioUsuario.setDataAbertura(OffsetDateTime.now());
        questionarioUsuario.setDataFechamento(OffsetDateTime.now().plusMinutes(1));
        
        return questionarioUsuario;
	}
	
	private void persistirQuestionarioUsuario(Curso curso, QuestionarioUsuario questionarioUsuario) {
		curso.getQuestionario().getQuestionariosUsuarios().add(questionarioUsuario);
        try {
        	cursoRepository.saveAndFlush(curso);
		} catch (DataIntegrityViolationException e) {
			throw new QuestionarioUsuarioDuplicadoException("Já existe um questionário associado a essa matrícula para o questionário informado.");
		}
	}
	
	private QuestionarioUsuario recuperarQuestionarioPersistido(Curso curso, Matricula matricula) {
		return curso.getQuestionario().getQuestionariosUsuarios().stream()
				.filter(q -> q.getMatricula().getId().equals(matricula.getId()))
				.findFirst()
				.orElseThrow(() -> new QuestionarioNaoEncontradoException("Erro ao recuperar o questionario"));
	}
	
	private boolean isQuestionarioExpirado(QuestionarioUsuario questionarioUsuario) {
		return questionarioUsuario.getDataFechamento().isBefore(OffsetDateTime.now());
	}
	
	private Matricula buscarMatriculaDoUsuario(Curso curso, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        return Optional.ofNullable(usuarioRepository.findByCursoAndUsuario(curso, usuario))
                .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso e usuário"));
    }
	
	private QuestionarioUsuario buscarQuestionarioUsuario(Curso curso, Matricula matricula, Long questionarioId) {
        return curso.getQuestionario().getQuestionariosUsuarios().stream()
                .filter(q -> q.getMatricula().getId().equals(matricula.getId())
                        && q.getQuestionario().getId().equals(questionarioId))
                .findFirst()
                .orElseThrow(() -> new QuestionarioNaoEncontradoException("Questionário do usuário não encontrado"));
    }
	
	
	
	
	public List<RespostaDTO> verificarRespostas(Long cursoId, Long questionarioId, Long usuarioId, List<RespostaInput> respostasInput)
			throws Exception {

		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

		Questionario questionario = curso.getQuestionario();
		
		if (questionario == null) {
			throw new QuestionarioNaoEncontradoException("O curso não tem um questionário.");
		}
		
		Matricula matricula = buscarMatriculaDoUsuario(curso, usuarioId);
		QuestionarioUsuario questionarioUsuario = buscarQuestionarioUsuario(curso, matricula, questionarioId);

		if (questionarioUsuario.getFinalizado() == Boolean.TRUE) {
            throw new QuestionarioExpiradoException("O questionário já foi finalizado.");
        }
		
		List<RespostaDTO> resultados = new ArrayList<>();
		int acertos = 0;
		int totalPerguntas = questionario.getPerguntas().size();

		for (RespostaInput respostaInput : respostasInput) {
			Pergunta pergunta = questionario.getPerguntas().stream()
					.filter(p -> p.getId().equals(respostaInput.getPerguntaId()))
					.findFirst()
					.orElseThrow(() -> new Exception("Pergunta não encontrada"));

			boolean respostaCorreta = pergunta.verificarResposta(respostaInput.getResposta());

			RespostaDTO respostaDTO = new RespostaDTO();
			respostaDTO.setPerguntaId(respostaInput.getPerguntaId());
			respostaDTO.setResposta(respostaInput.getResposta());
			respostaDTO.setIsCorreta(respostaCorreta);

			resultados.add(respostaDTO);
			
			if (respostaCorreta) {
                acertos++;
            }
		}
		
		double nota = (totalPerguntas > 0) ? ((double) acertos / totalPerguntas) * 10 : 0;
		questionarioUsuario.setNota(nota);
		questionarioUsuario.setFinalizado(Boolean.TRUE);

		cursoRepository.save(curso);
		
		return resultados;
	}
	

	public Questionario buscarOuFalhar(Long cursoId) {
		return cursoRepository.findQuestionarioById(cursoId)
				.orElseThrow(() -> new QuestionarioNaoEncontradoException(cursoId));
	}

}
