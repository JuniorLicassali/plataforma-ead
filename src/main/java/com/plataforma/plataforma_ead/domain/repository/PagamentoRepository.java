package com.plataforma.plataforma_ead.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Pagamento;
import com.plataforma.plataforma_ead.domain.model.StatusPagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

	@Query("SELECT COUNT(p) > 0 FROM Pagamento p WHERE p.matriculaId = :matriculaId AND p.statusPagamento IN :statusPagamento")
    boolean existsByMatriculaIdAndStatusPagamentoIn(
    	@Param("matriculaId") Long matriculaId,
        @Param("statusPagamento") List<StatusPagamento> statusPagamento);
	
}
