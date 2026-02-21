package com.plataforma.plataforma_ead.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.dto.input.EsqueciSenhaInput;
import com.plataforma.plataforma_ead.api.dto.input.NovaSenhaInput;
import com.plataforma.plataforma_ead.api.openapi.controller.RecuperarSenhaControllerOpenApi;
import com.plataforma.plataforma_ead.domain.service.SolicitacaoSenhaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recuperar-senha")
@RequiredArgsConstructor
public class RecuperarSenhaController implements RecuperarSenhaControllerOpenApi {

    private final SolicitacaoSenhaService solicitacaoSenhaService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void solicitar(@RequestBody @Valid EsqueciSenhaInput input) {
        solicitacaoSenhaService.solicitar(input.getEmail());
    }
    
    @Override
    @PutMapping("/alterar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterar(@RequestBody @Valid NovaSenhaInput input) {
        solicitacaoSenhaService.alterarComCodigo(input);
    }
    
}