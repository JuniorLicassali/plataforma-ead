package com.plataforma.plataforma_ead.infrastructure.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.plataforma.plataforma_ead.domain.filter.CursoFilter;
import com.plataforma.plataforma_ead.domain.model.Curso;

import jakarta.persistence.criteria.Predicate;

public class CursoSpecs {
	
	public static Specification<Curso> usandoFiltro(CursoFilter filtro) {
		return (root, query, builder) -> {
			
			if (query.getResultType().equals(Curso.class)) {
	            // root.fetch("", JoinType.LEFT);
	        }
			
			var predicates = new ArrayList<Predicate>();
			
			if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
				predicates.add(builder.like(builder.lower(root.get("nome")), 
						"%" + filtro.getNome().toLowerCase() + "%"));
			}
			
			if (filtro.getAtivo() != null) {
				predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
			}
			
			if (filtro.getPrecoMin() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("preco"), filtro.getPrecoMin()));
			}
			
			if (filtro.getPrecoMax() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("preco"), filtro.getPrecoMax()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	
}
