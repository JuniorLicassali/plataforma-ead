package com.plataforma.plataforma_ead.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.exception.UsuarioNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Grupo;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final CadastroGrupoService grupoService;
	
	private final EntityManager entityManager;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		entityManager.detach(usuario);

		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());


		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", 
					usuario.getEmail()));
		}

			return usuarioRepository.save(usuario);
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario  = buscarOuFalhar(usuarioId);

		if (usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}

		usuario.setSenha(novaSenha);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
}
