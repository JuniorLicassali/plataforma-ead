package com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AsaasWebhookRequest {
	
	private String id;
    private String event;
    private String dateCreated;
    private AsaasPaymentData payment;

}
