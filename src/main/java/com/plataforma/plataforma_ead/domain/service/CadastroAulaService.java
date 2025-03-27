package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;

@Service
public class CadastroAulaService {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private CadastroModuloService moduloService;
	
	@Transactional
	public Aula salvar(Aula aula, Long cursoId, Long moduloId) {
		Curso curso = cursoService.buscarOuFalhar(cursoId);
		Modulo modulo = moduloService.buscarOuFalhar(moduloId);
		
		if (!modulo.getCurso().getId().equals(cursoId)) {
            throw new IllegalArgumentException("O módulo com ID " + moduloId + " não pertence ao curso com ID " + cursoId);
        }
		
		aula.setId(null);
		aula.setModulo(modulo);
		modulo.getAulas().add(aula);
//		curso.getModulos().add(modulo);
		
		cursoRepository.save(curso);
		
		return aula;
	}
	
	@Transactional
	public void excluir(Long aulaId) {
		cursoRepository.deleteAulaById(aulaId);
	}
	
	public Aula buscarOuFalhar(Long aulaId) {
		return cursoRepository.findAulaById(aulaId)
				.orElseThrow(() -> new NegocioException("Aula nao encontrada"));
	}
	
}
