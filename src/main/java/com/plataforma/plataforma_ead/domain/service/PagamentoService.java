package com.plataforma.plataforma_ead.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.PagamentoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.repository.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private MatriculaService matriculaService;
	
	
	@Transactional
	public Pagamento criarPagamento(Long matriculaId, MetodoPagamento metodoPagamento) {
		Matricula matricula = matriculaService.buscarOuFalhar(matriculaId);
		
		if (pagamentoRepository.existsByMatriculaIdAndStatusPagamentoIn(matriculaId, 
		        List.of(StatusPagamento.PAGAMENTO_PENDENTE, StatusPagamento.PAGAMENTO_CONCLUIDO))) {
		        throw new IllegalStateException("Já existe um pagamento pendente ou concluído para esta matrícula.");
		    }
		
		Pagamento pagamento = new Pagamento();
		pagamento.setPreco(matricula.getCurso().getPreco());
		pagamento.setMatriculaId(matriculaId);
		pagamento.setMetodoPagamento(metodoPagamento);
		
		pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
		pagamento = pagamentoRepository.save(pagamento);
		
		/*
		 * processa pagamento api
		 * 
		 */
		
		return pagamentoRepository.save(pagamento);
		
	}
	

	@Transactional
	public Pagamento confirmarPagamento(Pagamento pagamentoCurso) {
		Pagamento pagamento = buscarOuFalhar(pagamentoCurso.getId());
		
		if(pagamento.getStatusPagamento() == StatusPagamento.PAGAMENTO_CONCLUIDO) {
			throw new IllegalStateException("O pagamento já está concluído.");
		}
		
		pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_CONCLUIDO);		
		return pagamentoRepository.save(pagamento);
	}
	
	@Transactional
	public Pagamento escolherMetodoPagamento(Long pagamentoId, MetodoPagamento novoMetodo) {
		Pagamento pagamento = buscarOuFalhar(pagamentoId);
		
		if (pagamento.getStatusPagamento() == StatusPagamento.PAGAMENTO_CONCLUIDO) {
	        throw new IllegalStateException("Não é possível alterar o método de pagamento de um pagamento concluído.");
	    }
		
		if(pagamento.getMetodoPagamento() != null && pagamento.getMetodoPagamento().naoPodeAlterarPara(novoMetodo)) {
			throw new IllegalStateException("Não é possível alterar o método de pagamento de " 
			+ pagamento.getMetodoPagamento().getDescricao() 
			+ " pra " + novoMetodo.getDescricao());
		}
		
		pagamento.setMetodoPagamento(novoMetodo);
		
		return pagamentoRepository.save(pagamento);
	}
	
	public Pagamento buscarOuFalhar(Long pagamentoId) {
        return pagamentoRepository.findById(pagamentoId)
            .orElseThrow(() -> new PagamentoNaoEncontradoException(pagamentoId));
    }
	
}
