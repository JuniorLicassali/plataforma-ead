package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.PagamentoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Transactional
	public Pagamento criarPagamento(Long usuarioId, Long cursoId, MetodoPagamento metodoPagamento) {
		
		Matricula matricula = matriculaRepository.findByUsuarioIdAndCursoIdAndStatus(usuarioId, cursoId, StatusMatricula.PAGAMENTO_PENDENTE)
				.orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada para o curso: " + cursoId));
		
		boolean pagamentoExistente = matricula.getPagamentos().stream()
		        .anyMatch(p -> p.getStatusPagamento() == StatusPagamento.PAGAMENTO_PENDENTE);
		
		 if (pagamentoExistente) {
		        throw new NegocioException("Já existe um pagamento pendente para esta matrícula.");
		 }
		
		Pagamento pagamento = new Pagamento();
		pagamento.setMatricula(matricula);
		pagamento.setPreco(matricula.getCurso().getPreco());
		pagamento.setMetodoPagamento(metodoPagamento);
		pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
		
		/*
		* retorno pagamento api
		* 
		*/
		
		pagamento = pagamentoRepository.saveAndFlush(pagamento);
		
		return pagamento;
	}

	
	@Transactional
	public Pagamento confirmarPagamento(Long pagamentoId) {
		Pagamento pagamento = buscarOuFalhar(pagamentoId);
		
		if(pagamento.getStatusPagamento() == StatusPagamento.PAGAMENTO_PENDENTE) {
			pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_CONCLUIDO);
			
			Matricula matricula = pagamento.getMatricula();
			
			if(matricula !=null && matricula.getStatusMatricula() == StatusMatricula.PAGAMENTO_PENDENTE) {
				matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);
				
//				usuarioService.salvar(matricula.getUsuario());
				matriculaRepository.save(matricula);
			}
			
		} else {
			throw new NegocioException("O pagamento já está concluído.");
		}
		
		return pagamentoRepository.save(pagamento);
	}
    
    public Pagamento buscarOuFalhar(Long pagamentoId) {
		return pagamentoRepository.findById(pagamentoId)
				.orElseThrow(() -> new PagamentoNaoEncontradoException(pagamentoId));
	}
    
}