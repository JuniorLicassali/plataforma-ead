package com.plataforma.plataforma_ead.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.domain.service.PagamentoService;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasWebhookRequest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

@Hidden
@RestController
@RequestMapping("/payments-webhook")
@RequiredArgsConstructor
public class PagamentoWebhookController {

    private final PagamentoService pagamentoService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void receberConfirmacao(@RequestBody AsaasWebhookRequest asaasWebhook) {
    	pagamentoService.confirmarPagamento(asaasWebhook);
    	
    }
    
}