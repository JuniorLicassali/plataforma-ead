package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.PagamentoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Transactional
	public Pagamento criarPagamento(Long usuarioId, Long cursoId, MetodoPagamento metodoPagamento) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		Matricula matricula = usuario.getMatriculas().stream()
				.filter(m -> m.getCurso().getId().equals(cursoId) && m.getStatusMatricula() == StatusMatricula.PAGAMENTO_PENDENTE)
				.findFirst()
		        .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso: " + cursoId));
		
		Pagamento pagamento = new Pagamento();
		pagamento.setMatricula(matricula);
		pagamento.setPreco(matricula.getCurso().getPreco());
		pagamento.setMetodoPagamento(metodoPagamento);
		pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
		
		/*
		* retorno pagamento api
		* 
		*/
		
		return pagamentoRepository.save(pagamento);
	}

	
	@Transactional
	public Pagamento confirmarPagamento(Long pagamentoId) {
		Pagamento pagamento = buscarOuFalhar(pagamentoId);
		
		if(pagamento.getStatusPagamento() == StatusPagamento.PAGAMENTO_PENDENTE) {
			pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_CONCLUIDO);
		} else {
			throw new IllegalStateException("O pagamento já está concluído.");
		}
		
		return pagamentoRepository.save(pagamento);
	}
    
    public Pagamento buscarOuFalhar(Long pagamentoId) {
		return pagamentoRepository.findById(pagamentoId)
				.orElseThrow(() -> new PagamentoNaoEncontradoException(pagamentoId));
	}
    
}