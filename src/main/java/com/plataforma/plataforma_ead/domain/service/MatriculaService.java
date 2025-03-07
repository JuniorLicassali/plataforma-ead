package com.plataforma.plataforma_ead.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

@Service
public class MatriculaService {
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario matricularUsuario(Long usuarioId, Long cursoId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		Curso curso = cursoService.buscarOuFalhar(cursoId);
		
		Matricula matricula = new Matricula();
		matricula.setUsuario(usuario);
		matricula.setCurso(curso);
		matricula.setDataMatricula(OffsetDateTime.now());
		matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_PENDENTE);
		
		usuario.getMatriculas().add(matricula);
		curso.getMatriculas().add(matricula);
		
		return usuarioService.salvar(usuario);
	}
	
	@Transactional
	public void validarStatusMatricula(Long pagamentoId) {
		Pagamento pagamento = pagamentoService.buscarOuFalhar(pagamentoId);
		if (pagamento.getStatusPagamento() == StatusPagamento.PAGAMENTO_CONCLUIDO) {
            Usuario usuario = usuarioRepository.findByMatriculaPagamentoId(pagamentoId)
                .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o pagamento com ID: " + pagamentoId));

            Matricula matricula = usuario.getMatriculas().stream()
                .filter(m -> pagamentoId.equals(m.getPagamentoId()))
                .findFirst()
                .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o pagamento com ID: " + pagamentoId));

            matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);

            usuarioRepository.save(usuario);
        }
    }
		
	
	public Matricula buscarOuFalhar(Long matriculaId) {
		Usuario usuario = usuarioRepository.findByMatriculaId(matriculaId).orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
		
		return usuario.getMatriculas().stream().filter(matricula -> matricula.getId().equals(matriculaId))
					.findFirst()
					.orElseThrow(() -> new MatriculaNaoEncontradaException(matriculaId));
	}
	
}
