package com.plataforma.plataforma_ead.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.dto.PagamentoWebhookDTO;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;
import com.plataforma.plataforma_ead.domain.service.PagamentoService;

@RestController
@RequestMapping("/payments-webhook")
public class PagamentoWebhookController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Void> receberConfirmacao(@RequestBody PagamentoWebhookDTO pagamentoWebhook) {
    	StatusPagamento statusPagamento = StatusPagamento.valueOf(pagamentoWebhook.getStatusPagamento());
    	
    	if(statusPagamento == StatusPagamento.PAGAMENTO_CONCLUIDO) {
    		pagamentoService.confirmarPagamento(pagamentoWebhook.getPagamentoId());
    	} else {
    		return ResponseEntity.badRequest().build();
    	}
    	
        return ResponseEntity.ok().build();
    }
    
}