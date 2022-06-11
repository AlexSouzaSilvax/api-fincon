package com.fincon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fincon.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Query(value = "select * from lancamento l order by l.numero_parcela", nativeQuery = true)
	List<Lancamento> findAllOrderNumeroParcela();

	//@Query(value = "select id, descricao, valor, tipo_pagamento, tipo_lancamento, data_lancamento, pago from lancamento where mes_referencia = :pMesReferencia and ano_referencia = :pAnoReferencia order by 1 desc", nativeQuery = true)
	@Query(value = "select * from lancamento where mes_referencia = :pMesReferencia and ano_referencia = :pAnoReferencia order by 1 desc", nativeQuery = true)
	List<Lancamento> findfindListMain(@Param("pMesReferencia") int pMesReferencia, @Param("pAnoReferencia") int pAnoReferencia);
}	