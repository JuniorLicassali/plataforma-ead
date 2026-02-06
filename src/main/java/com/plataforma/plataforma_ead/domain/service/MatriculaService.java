package com.plataforma.plataforma_ead.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.UsuarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatriculaService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final MatriculaRepository matriculaRepository;
	
	@Transactional
    public Matricula matricularUsuario(Long usuarioId, Curso curso) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        
        boolean usuarioJaMatriculado = matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, curso.getId()).isPresent();
        
        if (usuarioJaMatriculado) {
            throw new NegocioException("O usuário já está matriculado neste curso.");
        }

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);
        matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_PENDENTE);
        matricula.setDataMatricula(OffsetDateTime.now());
        
        matricula = matriculaRepository.save(matricula);

        return matricula;
    }
	
	public boolean isUsuarioMatriculado(Long usuarioId, Long cursoId) {
		boolean usuarioMatriculado = matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).stream()
				.anyMatch(matricula -> matricula.getStatusMatricula().equals(StatusMatricula.PAGAMENTO_CONFIRMADO));
		
		return usuarioMatriculado;
	}
	
	@Transactional
    public void confirmarMatricula(Long matriculaId) {
		Matricula matricula = buscarOuFalhar(matriculaId);
        matricula.confirmar();
        matriculaRepository.save(matricula);
    }
	
	public Matricula buscarOuFalhar(Long matriculaId) {
		return matriculaRepository.findById(matriculaId).orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
	}
	
}
