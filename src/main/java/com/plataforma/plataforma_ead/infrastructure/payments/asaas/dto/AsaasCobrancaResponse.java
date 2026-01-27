package com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AsaasCobrancaResponse {

    private String id;
    private String status;
    private String invoiceUrl;
}