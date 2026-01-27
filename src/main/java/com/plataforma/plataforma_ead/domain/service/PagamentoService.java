package com.plataforma.plataforma_ead.domain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.PagamentoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.gateway.AsaasGateway;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.PagamentoRepository;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasCobrancaResponse;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasWebhookRequest;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private AsaasGateway asaasGateway;
	
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
		
		var usuario = matricula.getUsuario();
		
		if (usuario.getAsaasCustomerId() == null) {
            String customerId = asaasGateway.criarCustomer(matricula.getUsuario());
            usuario.setAsaasCustomerId(customerId);
            pagamento.getMatricula().setUsuario(usuario);
        }
		
		AsaasCobrancaResponse response =
                asaasGateway.criarCobranca(
                        usuario.getAsaasCustomerId(),
                        matricula.getCurso().getPreco(),
                        LocalDate.now().plusDays(3), metodoPagamento
                );
		
		pagamento.setAsaasPaymentId(response.getId());
        pagamento.setAsaasInvoiceUrl(response.getInvoiceUrl());
		
		//pagamento = pagamentoRepository.saveAndFlush(pagamento);
        pagamento = pagamentoRepository.save(pagamento);
        
        System.out.println(response);
		
		return pagamento;
	}

	
	@Transactional
	public void confirmarPagamento(AsaasWebhookRequest webhook) {
		
		if("PAYMENT_CONFIRMED".equals(webhook.getEvent())) {
			Pagamento pagamentoEx = buscarOuFalhar(webhook.getPayment().getId());
			
			if (pagamentoEx.getStatusPagamento() == StatusPagamento.PAGAMENTO_CONCLUIDO) {
		        return;
		    }
			
			pagamentoEx.setStatusPagamento(StatusPagamento.PAGAMENTO_CONCLUIDO);
			
			Matricula matricula = pagamentoEx.getMatricula();
			
			if(matricula !=null && matricula.getStatusMatricula() == StatusMatricula.PAGAMENTO_PENDENTE) {
				matricula.setStatusMatricula(StatusMatricula.PAGAMENTO_CONFIRMADO);
				
				matriculaRepository.save(matricula);
			}
			
			pagamentoRepository.save(pagamentoEx);
		}
		
	}
    
    public Pagamento buscarOuFalhar(Long pagamentoId) {
		return pagamentoRepository.findById(pagamentoId)
				.orElseThrow(() -> new PagamentoNaoEncontradoException(pagamentoId));
	}
    
    public Pagamento buscarOuFalhar(String paymentId) {
		return pagamentoRepository.findByPaymentId(paymentId)
				.orElseThrow(() -> new PagamentoNaoEncontradoException(paymentId));
	}
    
}