package com.plataforma.plataforma_ead.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_ead.api.dto.RespostaDTO;
import com.plataforma.plataforma_ead.api.dto.input.RespostaInput;
import com.plataforma.plataforma_ead.domain.exception.CursoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.EntidadeEmUsoException;
import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioExpiradoException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.QuestionarioUsuarioDuplicadoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Pergunta;
import com.plataforma.plataforma_ead.domain.model.Questionario;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;
import com.plataforma.plataforma_ead.domain.model.event.CertificadoEmissaoEvent;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.QuestionarioRepository;

import jakarta.transaction.Transactional;

@Service
public class CadastroQuestionarioService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private QuestionarioRepository questionarioRepository;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Questionario criarQuestionario(Long cursoId, Questionario questionario) {
		Curso curso = cursoService.buscarOuFalhar(cursoId);
		
		if(curso.getQuestionario() != null) {
			throw new EntidadeEmUsoException("O curso já tem uma avaliação final associada.");
		} 
		
		curso.setQuestionario(questionario);
		Questionario novoQuestionario = questionarioRepository.save(questionario);
		cursoService.salvar(curso);
		
		return novoQuestionario;
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
	

	@Transactional
	public QuestionarioUsuario iniciarQuestionario(Long cursoId, Long usuarioId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(cursoId));
        
//        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Matricula matricula = matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId)
        		.orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso e usuário"));
        
        Questionario questionario = curso.getQuestionario();
        if (questionario == null) {
            throw new QuestionarioNaoEncontradoException("Questionário não encontrado para o curso");
        }
        
        QuestionarioUsuario questionarioUsuarioExistente = buscarQuestionarioExistente(matricula, questionario);
        
        if(questionarioUsuarioExistente != null) {
        	if(isQuestionarioExpirado(questionarioUsuarioExistente)){
        		throw new QuestionarioExpiradoException("O questionário expirou.");
        	}
        	return questionarioUsuarioExistente;
        }
        
        QuestionarioUsuario novoQuestionario = criarNovoQuestionarioUsuario(questionario, matricula);
        persistirQuestionarioUsuario(questionario, novoQuestionario);
        
        return recuperarQuestionarioPersistido(questionario, matricula);
    }
	
	@Transactional
	public List<RespostaDTO> verificarRespostas(Long cursoId, Long questionarioId, Long usuarioId, List<RespostaInput> respostasInput)
			throws Exception {

		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new CursoNaoEncontradoException("Curso não encontrado"));

		Questionario questionario = questionarioRepository.findById(questionarioId)
				.orElseThrow(() -> new QuestionarioNaoEncontradoException("Questionário não encontrado"));
		
		if (questionario == null) {
			throw new QuestionarioNaoEncontradoException("O curso não tem um questionário.");
		}
		
		Matricula matricula = buscarMatriculaDoUsuario(curso, usuarioId);
		QuestionarioUsuario questionarioUsuario = buscarQuestionarioUsuario(matricula, questionarioId);

		if (questionarioUsuario.getFinalizado() == Boolean.TRUE) {
            throw new QuestionarioExpiradoException("O questionário já foi finalizado.");
        }
		
		List<RespostaDTO> resultados = new ArrayList<>();
	    int acertos = processarRespostas(respostasInput, questionario, resultados);
		
		double nota = calcularNota(acertos, questionario.getPerguntas().size());
		
		questionarioUsuario.setNota(nota);
		questionarioUsuario.setFinalizado(Boolean.TRUE);

		cursoRepository.save(curso);
		if (nota > 7.0) {
	        publisher.publishEvent(new CertificadoEmissaoEvent(matricula));
	    }
		
		return resultados;
	}
	
	
	
	private QuestionarioUsuario buscarQuestionarioExistente(Matricula matricula, Questionario questionario) {
		return questionario.getQuestionariosUsuarios().stream()
				.filter(q -> q.getMatricula()
						.getId().equals(matricula.getId())).findFirst()
				.orElse(null);
	}
	
	private QuestionarioUsuario criarNovoQuestionarioUsuario(Questionario questionario, Matricula matricula) {
        QuestionarioUsuario questionarioUsuario = new QuestionarioUsuario();
        questionarioUsuario.setMatricula(matricula);
        questionarioUsuario.setQuestionario(questionario);
        questionarioUsuario.setDataAbertura(OffsetDateTime.now());
        questionarioUsuario.setDataFechamento(OffsetDateTime.now().plusMinutes(1));
        
        return questionarioUsuario;
	}
	
	private void persistirQuestionarioUsuario(Questionario questionario, QuestionarioUsuario questionarioUsuario) {
        try {
        	questionario.getQuestionariosUsuarios().add(questionarioUsuario);
        	questionarioRepository.saveAndFlush(questionario);
		} catch (DataIntegrityViolationException e) {
			throw new QuestionarioUsuarioDuplicadoException("Já existe um questionário associado a essa matrícula para o questionário informado.");
		}
	}
	
	private QuestionarioUsuario recuperarQuestionarioPersistido(Questionario questionario, Matricula matricula) {
		return questionario.getQuestionariosUsuarios().stream()
	            .filter(q -> q.getMatricula().getId().equals(matricula.getId()))
	            .findFirst()
	            .orElseThrow(() -> new QuestionarioNaoEncontradoException("Erro ao recuperar o questionário"));
	}
	
	private boolean isQuestionarioExpirado(QuestionarioUsuario questionarioUsuario) {
		return questionarioUsuario.getDataFechamento().isBefore(OffsetDateTime.now());
	}
	
	private Matricula buscarMatriculaDoUsuario(Curso curso, Long usuarioId) {
		return matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, curso.getId())
				.orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso e usuário") );
	
    }
	
	private QuestionarioUsuario buscarQuestionarioUsuario(Matricula matricula, Long questionarioId) {
		Questionario questionario = buscarOuFalhar(questionarioId);
		
		return questionario.getQuestionariosUsuarios().stream()
			.filter(q -> q.getMatricula().getId().equals(matricula.getId())
                    && q.getQuestionario().getId().equals(questionarioId))
            .findFirst()
            .orElseThrow(() -> new QuestionarioNaoEncontradoException("Questionário do usuário não encontrado"));
    }
	

	private int processarRespostas(List<RespostaInput> respostasInput, Questionario questionario, List<RespostaDTO> resultados) throws Exception {
	    int acertos = 0;

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

	    return acertos;
	}
	
	private double calcularNota(int acertos, int totalPerguntas) {
	    return (totalPerguntas > 0) ? ((double) acertos / totalPerguntas) * 10 : 0;
	}
	
	public Questionario buscarOuFalhar(Long cursoId) {
		return cursoRepository.findQuestionarioById(cursoId)
				.orElseThrow(() -> new QuestionarioNaoEncontradoException(cursoId));
	}

}
