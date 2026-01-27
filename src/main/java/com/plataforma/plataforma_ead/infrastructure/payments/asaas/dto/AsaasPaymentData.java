package com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AsaasPaymentData {

    private String object;
    private String id;
}