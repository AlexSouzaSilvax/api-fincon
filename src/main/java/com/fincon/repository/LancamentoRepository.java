package com.fincon.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fincon.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Query(value = "select * from lancamento l order by l.numero_parcela", nativeQuery = true)
	List<Lancamento> findAllOrderNumeroParcela();

	@Query(value = "select * from lancamento where id_usuario = :pIdUsuario and mes_referencia = :pMesReferencia and ano_referencia = :pAnoReferencia order by 1 desc", nativeQuery = true)
	List<Lancamento> findListMain(
			@Param("pIdUsuario") long pIdUsuario,
			@Param("pMesReferencia") int pMesReferencia,
			@Param("pAnoReferencia") int pAnoReferencia);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update lancamento set ano_referencia = :pAnoReferencia, categoria = :pCategoria, data_prevista_pagamento = :pDataPrevistaPagamento, data_vencimento = :pDataVencimento, descricao = :pDescricao, mensal = :pMensal, mes_referencia = :pMesReferencia, observacao = :pObservacao, pago = :pPago, quantidade_parcelas = :pQuantidadeParcelas, tipo_lancamento = :pTipoLancamento, tipo_pagamento = :pTipoPagamento, valor = :pValor where id = :pId", nativeQuery = true)
	void updateAllById(
			@Param("pAnoReferencia") int pAnoReferencia,
			@Param("pCategoria") int pCategoria,
			@Param("pDataPrevistaPagamento") LocalDateTime pDataPrevistaPagamento,
			@Param("pDataVencimento") LocalDateTime pDataVencimento,
			@Param("pDescricao") String pDescricao,
			@Param("pMensal") boolean pMensal,
			@Param("pMesReferencia") int pMesReferencia,
			@Param("pObservacao") String pObservacao,
			@Param("pPago") boolean pPago,
			@Param("pQuantidadeParcelas") int pQuantidadeParcelas,
			@Param("pTipoLancamento") int pTipoLancamento,
			@Param("pTipoPagamento") int pTipoPagamento,
			@Param("pValor") BigDecimal pValor,
			@Param("pId") Long pId);

	@Query(value = "SELECT * FROM lancamento WHERE id_usuario = :pIdUsuario AND mes_referencia = :pMesReferencia AND ano_referencia = :pAnoReferencia AND descricao = 'Saldo do Mês Anterior'", nativeQuery = true)
	List<Lancamento> findByLancamentoSaldoMesAnterior(@Param("pIdUsuario") long pIdUsuario,
			@Param("pMesReferencia") int pMesReferencia,
			@Param("pAnoReferencia") int pAnoReferencia);

	@Query(value = "select ( (SELECT SUM(valor) FROM lancamento WHERE id_usuario = :pIdUsuario AND mes_referencia = :pMesReferencia AND ano_referencia = :pAnoReferencia and tipo_lancamento = 0 AND pago = true) - (SELECT SUM(valor) FROM lancamento WHERE id_usuario = :pIdUsuario AND mes_referencia = :pMesReferencia AND ano_referencia = :pAnoReferencia and tipo_lancamento = 1 AND pago = true) ) as saldo", nativeQuery = true)
	String buscaTotalLancamentoPorMes(long pIdUsuario, int pMesReferencia, int pAnoReferencia);
}