package com.plataforma.plataforma_ead.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.plataforma.plataforma_ead.api.assembler.FotoUsuarioDTOAssembler;
import com.plataforma.plataforma_ead.api.dto.FotoUsuarioDTO;
import com.plataforma.plataforma_ead.api.dto.input.FotoUsuarioInput;
import com.plataforma.plataforma_ead.domain.exception.EntidadeNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.model.FotoUsuario;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.service.CadastroUsuarioService;
import com.plataforma.plataforma_ead.domain.service.CatalogoFotoUsuarioService;
import com.plataforma.plataforma_ead.domain.service.FotoStorageService;
import com.plataforma.plataforma_ead.domain.service.FotoStorageService.FotoRecuperada;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioFotoController {

	
	@Autowired
	private CatalogoFotoUsuarioService catalogoFotoUsuario;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Autowired
	private FotoUsuarioDTOAssembler fotoUsuarioDTOAssembler;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoUsuarioDTO atualizarFoto(@PathVariable Long usuarioId, @Valid FotoUsuarioInput fotoUsuarioInput) throws IOException {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		MultipartFile arquivo = fotoUsuarioInput.getArquivo();
		
		FotoUsuario foto = new FotoUsuario();
		foto.setUsuario(usuario);
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoUsuario fotoSalva = catalogoFotoUsuario.salvar(foto, arquivo.getInputStream());
		
		return fotoUsuarioDTOAssembler.toModel(fotoSalva);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long usuarioId) {
		catalogoFotoUsuario.excluir(usuarioId);
	}
	
	@GetMapping
	public FotoUsuarioDTO buscar(@PathVariable Long usuarioId) {
		FotoUsuario fotoUsuario = catalogoFotoUsuario.buscarOuFalhar(usuarioId);
		
		return fotoUsuarioDTOAssembler.toModel(fotoUsuario);
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servir(@PathVariable Long usuarioId, @RequestHeader(name = "accept") String acceptHeader) 
					throws HttpMediaTypeNotAcceptableException {
		try {
			FotoUsuario fotoUsuario = catalogoFotoUsuario.buscarOuFalhar(usuarioId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoUsuario.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoUsuario.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, 
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
	
}