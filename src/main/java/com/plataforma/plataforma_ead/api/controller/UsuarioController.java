package com.plataforma.plataforma_ead.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plataforma.plataforma_ead.api.assembler.MatriculaDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.UsuarioDTOAssembler;
import com.plataforma.plataforma_ead.api.assembler.UsuarioInputDisassembler;
import com.plataforma.plataforma_ead.api.dto.MatriculaDTO;
import com.plataforma.plataforma_ead.api.dto.UsuarioDTO;
import com.plataforma.plataforma_ead.api.dto.input.MatriculaInput;
import com.plataforma.plataforma_ead.api.dto.input.SenhaInput;
import com.plataforma.plataforma_ead.api.dto.input.UsuarioComSenhaInput;
import com.plataforma.plataforma_ead.api.dto.input.UsuarioInput;
import com.plataforma.plataforma_ead.api.openapi.controller.UsuarioControllerOpenApi;
import com.plataforma.plataforma_ead.core.security.CheckSecurity;
import com.plataforma.plataforma_ead.core.security.PlataformaSecurity;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.Usuario;
import com.plataforma.plataforma_ead.domain.repository.MatriculaRepository;
import com.plataforma.plataforma_ead.domain.repository.UsuarioRepository;
import com.plataforma.plataforma_ead.domain.service.CadastroCursoService;
import com.plataforma.plataforma_ead.domain.service.CadastroUsuarioService;
import com.plataforma.plataforma_ead.domain.service.MatriculaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi {
	
	private final UsuarioRepository usuarioRepository;
	private final CadastroUsuarioService usuarioService;
	private final UsuarioDTOAssembler usuarioDTOAssembler;
	private final UsuarioInputDisassembler usuarioInputDisassembler;
	private final MatriculaDTOAssembler matriculaDTOAssembler;
	private final CadastroCursoService cursoService;
	private final MatriculaService matriculaService;
	private final MatriculaRepository matriculaRepository;
	private final PlataformaSecurity plataformaSecurity;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public List<UsuarioDTO> listar() {
		List<UsuarioDTO> todosUsuarios = usuarioDTOAssembler.toCollectionDTO(usuarioRepository.findAll());
		
		return todosUsuarios;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		UsuarioDTO usuario = usuarioDTOAssembler.toDTO(usuarioService.buscarOuFalhar(usuarioId));
		
		return usuario;
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = usuarioService.salvar(usuario);
		
		return usuarioDTOAssembler.toDTO(usuario);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@Override
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		
		return usuarioDTOAssembler.toDTO(usuarioAtual);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@Override
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
	@Override
	@GetMapping("/{usuarioId}/matriculas")
	public List<MatriculaDTO> listarMatriculaPorUsuario(@PathVariable Long usuarioId) {
		return matriculaDTOAssembler.toCollectionDTO(matriculaRepository.findMatriculasByUsuarioId(usuarioId));
	}
	
	@CheckSecurity.Matricula.PodeMatricular
	@Override
	@PostMapping("/{usuarioId}/matriculas")
	@ResponseStatus(HttpStatus.CREATED)
	public MatriculaDTO matricularUsuario(@PathVariable Long usuarioId, @RequestBody @Valid MatriculaInput matriculaInput) throws Exception {
		Long idDoUsuarioLogado = plataformaSecurity.getUsuarioId();
		Curso curso = cursoService.buscarOuFalhar(matriculaInput.getCursoId());
		
		if(curso.getAtivo() != true) {
			throw new NegocioException("Curso inativo");
		}
		
		Matricula matricula = matriculaService.matricularUsuario(idDoUsuarioLogado, curso);
		
		return matriculaDTOAssembler.toDTO(matricula);
	}
	

}
