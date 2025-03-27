package com.plataforma.plataforma_ead.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.domain.service.GerarCertificadoService;

@RestController
@RequestMapping(path = "/certificados", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificadoController {

	@Autowired
    private GerarCertificadoService certificadoService;

    @GetMapping("/matriculas/{matriculaId}")
    public ResponseEntity<Resource> emitirCertificado(@PathVariable Long matriculaId) throws Exception {

        byte[] certificadoBytes = certificadoService.gerarCertificado(matriculaId);

        ByteArrayResource resource = new ByteArrayResource(certificadoBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado.pdf");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(certificadoBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
	
}
