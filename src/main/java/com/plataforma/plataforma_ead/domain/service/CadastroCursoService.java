package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.CursoNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;

@Service
public class CadastroCursoService {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Transactional
	public Curso salvar(Curso curso) {
		return cursoRepository.save(curso);
	}
	
	@Transactional
	public void excluir(Long cursoId) {
		cursoRepository.deleteById(cursoId);
	}
	
	@Transactional
	public void ativar(Long cursoId) {
		Curso cursoAtual = buscarOuFalhar(cursoId);
		
		cursoAtual.ativar();
	}

	@Transactional
	public void inativar(Long cursoId) {
		Curso cursoAtual = buscarOuFalhar(cursoId);
		
		cursoAtual.inativar();
	}
	
	public Curso buscarOuFalhar(Long cursoId) {
		return cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(cursoId));
	}
	
}
