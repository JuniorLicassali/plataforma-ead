package com.plataforma.plataforma_ead.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plataforma.plataforma_ead.domain.exception.FotoUsuarioNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.model.FotoUsuario;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;
import com.plataforma.plataforma_ead.domain.service.FotoStorageService.NovaFoto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogoFotoUsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	private final FotoStorageService fotoStorage;
	
	@Transactional
	public FotoUsuario salvar(FotoUsuario foto, InputStream dadosArquivo) {
		Long usuarioId = foto.getUsuarioId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoUsuario> fotoExistente = usuarioRepository
				.findFotoById(usuarioId);
		
		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			usuarioRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto =  usuarioRepository.save(foto);
		usuarioRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeAquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();

		fotoStorage.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}

	public FotoUsuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findFotoById(usuarioId)
				.orElseThrow(() -> new FotoUsuarioNaoEncontradaException(usuarioId));
	}

	@Transactional
	public void excluir(Long usuarioId) {
		FotoUsuario foto = buscarOuFalhar(usuarioId);
		
		usuarioRepository.delete(foto);
		usuarioRepository.flush();

		fotoStorage.remover(foto.getNomeArquivo());
	}
	
}