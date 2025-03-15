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
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

@Service
public class MatriculaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
    public Matricula matricularUsuario(Long usuarioId, Curso curso) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        
        boolean usuarioJaMatriculado = usuario.getMatriculas().stream()
                .anyMatch(matricula -> matricula.getCurso().getId().equals(curso.getId()));
        
        if (usuarioJaMatriculado) {
            throw new IllegalStateException("O usuário já está matriculado neste curso.");
        }

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);
        matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_PENDENTE);
        matricula.setDataMatricula(OffsetDateTime.now());
        
        usuario.getMatriculas().add(matricula);
//        usuarioRepository.flush();

        return matricula;
    }
	
	@Transactional
    public void confirmarMatricula(Matricula matricula) {
        matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);
        usuarioRepository.save(matricula.getUsuario());
    }
	
	public Matricula buscarOuFalhar(Long matriculaId) {
		Usuario usuario = usuarioRepository.findByMatriculaId(matriculaId).orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
		
		return usuario.getMatriculas().stream()
				.filter(matricula -> matricula.getId().equals(matriculaId))
				.findFirst()
				.orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
	}
	
}
