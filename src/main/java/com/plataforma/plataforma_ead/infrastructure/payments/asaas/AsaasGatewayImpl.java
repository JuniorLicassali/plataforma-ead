package com.plataforma.plataforma_ead.infrastructure.payments.asaas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.plataforma.plataforma_ead.domain.gateway.AsaasGateway;
import com.plataforma.plataforma_ead.domain.model.MetodoPagamento;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.infrastructure.payments.asaas.dto.AsaasCobrancaResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsaasGatewayImpl implements AsaasGateway {

    private final RestTemplate asaasRestTemplate;

    @Override
    public String criarCustomer(Usuario usuario) {

        Map<String, Object> body = new HashMap<>();
        body.put("name", usuario.getNome());
        body.put("email", usuario.getEmail());
        body.put("cpfCnpj", "12345678909");

        ResponseEntity<Map> response =
                asaasRestTemplate.postForEntity(
                        "/customers",
                        body,
                        Map.class
                );

        return response.getBody().get("id").toString();
    }

    @Override
    public AsaasCobrancaResponse criarCobranca(
    		String customerId,
            BigDecimal valor,
            LocalDate dataVencimento,
            MetodoPagamento metodoPagamento) {

    	Map<String, Object> body = new HashMap<>();
        body.put("customer", customerId);
        body.put("billingType", mapearBillingType(metodoPagamento));
        body.put("value", valor);
        body.put("dueDate", dataVencimento.toString());

        ResponseEntity<AsaasCobrancaResponse> response =
                asaasRestTemplate.postForEntity(
                        "/payments",
                        body,
                        AsaasCobrancaResponse.class
                );

        return response.getBody();
    }
    
    private String mapearBillingType(MetodoPagamento metodoPagamento) {
        return switch (metodoPagamento) {
            case BOLETO -> "BOLETO";
            case PIX -> "PIX";
            case CARTAO_DE_CREDITO -> "CREDIT_CARD";
        };
    }
}