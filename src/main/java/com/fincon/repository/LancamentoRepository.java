package com.fincon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fincon.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Query(value = "select * from lancamento l order by l.numero_parcela", nativeQuery = true)
	List<Lancamento> findAllOrderNumeroParcela();
}	