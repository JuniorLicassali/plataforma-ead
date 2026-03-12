package com.plataforma.plataforma_ead.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recuperar-senha")
public class RecuperarSenhaViewController {

    @GetMapping
    public String paginaSolicitar() {
        return "pages/esqueceu-senha"; 
    }

    @GetMapping("/alterar")
    public String paginaAlterar() {
        return "pages/esqueceu-senha-alterar";
    }
}