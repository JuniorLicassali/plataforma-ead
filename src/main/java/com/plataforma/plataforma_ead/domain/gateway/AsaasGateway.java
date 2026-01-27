package com.plataforma.plataforma_ead.domain.gateway;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasCobrancaResponse;

public interface AsaasGateway {
	String criarCustomer(Usuario usuario);

    AsaasCobrancaResponse criarCobranca(
    		String customerId,
            BigDecimal valor,
            LocalDate dataVencimento,
            MetodoPagamento metodoPagamento
    );
}
