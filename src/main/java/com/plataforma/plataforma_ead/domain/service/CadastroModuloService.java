package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.ModuloNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;

@Service
public class CadastroModuloService {
	
	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;

	@Transactional
	public Modulo salvar(Modulo modulo, Long cursoId) {
		Curso curso = cursoService.buscarOuFalhar(cursoId);
		modulo.setCurso(curso);
		curso.getModulos().add(modulo);
		
		curso = cursoService.salvar(curso);
		return modulo;
	}
	
	@Transactional
	public void excluir(Long moduloId) {
		cursoRepository.deleteAulasByModuloId(moduloId);
		cursoRepository.deleteModuloById(moduloId);
	}
	
	public Modulo buscarOuFalhar(Long moduloId) {
		return cursoRepository.findModuloById(moduloId)
				.orElseThrow(() -> new ModuloNaoEncontradoException("Modulo não encotrado"));
	}
	
}
