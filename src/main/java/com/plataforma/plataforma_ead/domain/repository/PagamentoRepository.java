package com.plataforma.plataforma_ead.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, 
													JpaSpecificationExecutor<Pagamento> {
	
	@Query("select p from Pagamento p where p.asaasPaymentId = :paymentId")
	Optional<Pagamento> findByPaymentId(@Param("paymentId") String paymentId);
	
	boolean podeConsultar(Long pagamentoId, Long usuarioId);
	
}
