package com.plataforma.plataforma_ead.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.plataforma.plataforma_ead.domain.exception.ModuloNaoEncontradoException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.model.Aula;
import com.plataforma.plataforma_ead.domain.model.Curso;
import com.plataforma.plataforma_ead.domain.model.Modulo;
import com.plataforma.plataforma_ead.domain.repository.CursoRepository;
import com.plataforma.plataforma_ead.infrastructure.cloudinary.CloudinaryStorageService;

@Service
public class CadastroAulaService {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CadastroCursoService cursoService;
	
	@Autowired
	private CloudinaryStorageService cloudinaryService;
	
	@Transactional
	public Aula salvar(Long cursoId, Long moduloId, Aula aula, MultipartFile video) throws Exception {
		
		Curso curso = cursoService.buscarOuFalhar(cursoId);

		Modulo moduloEncontrado = curso.getModulos().stream().filter(m -> m.getId().equals(moduloId)).findFirst()
				.orElseThrow(() -> new ModuloNaoEncontradoException("Módulo de ID " + moduloId + " não encontrado."));
		
		String urlGerada = cloudinaryService.fazerUpload(video);

		
		Aula novaAula = new Aula();
		novaAula.setTitulo(aula.getTitulo());
		novaAula.setDescricao(aula.getDescricao());
		novaAula.setUrlVideo(urlGerada);
		novaAula.setModulo(moduloEncontrado);
		novaAula.setOrdem(aula.getOrdem());
		
		moduloEncontrado.getAulas().add(novaAula);
		//curso.getModulos().add(moduloEncontrado);

		cursoService.salvar(curso);
		
		return novaAula;
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
