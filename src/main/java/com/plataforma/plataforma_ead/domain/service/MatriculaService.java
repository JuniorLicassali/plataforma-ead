package com.plataforma.plataforma_ead.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.UsuarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

@Service
public class MatriculaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
//	@Transactional
//    public Matricula matricularUsuario(Long usuarioId, Curso curso) {
//        Usuario usuario = usuarioRepository.findById(usuarioId)
//            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
//        
//        boolean usuarioJaMatriculado = usuario.getMatriculas().stream()
//                .anyMatch(matricula -> matricula.getCurso().getId().equals(curso.getId()));
//        
//        if (usuarioJaMatriculado) {
//            throw new IllegalStateException("O usuário já está matriculado neste curso.");
//        }
//
//        Matricula matricula = new Matricula();
//        matricula.setUsuario(usuario);
//        matricula.setCurso(curso);
//        matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_PENDENTE);
//        matricula.setDataMatricula(OffsetDateTime.now());
//        
//        usuario.getMatriculas().add(matricula);
////        usuarioRepository.flush();
//
//        return matricula;
//    }
	
	@Transactional
    public Matricula matricularUsuario(Long usuarioId, Curso curso) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        
        boolean usuarioJaMatriculado = matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, curso.getId()).isPresent();
        
        if (usuarioJaMatriculado) {
            throw new IllegalStateException("O usuário já está matriculado neste curso.");
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
    public void confirmarMatricula(Matricula matricula) {
        matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);
        matriculaRepository.save(matricula);
    }
	
//	public Matricula buscarOuFalhar(Long matriculaId) {
//		Usuario usuario = usuarioRepository.findByMatriculaId(matriculaId).orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
//		
//		return usuario.getMatriculas().stream()
//				.filter(matricula -> matricula.getId().equals(matriculaId))
//				.findFirst()
//				.orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
//	}
	
	public Matricula buscarOuFalhar(Long matriculaId) {
		return matriculaRepository.findById(matriculaId).orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
	}
	
}
