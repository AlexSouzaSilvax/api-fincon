package com.fincon.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "Lancamento")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "tipo_lancamento", nullable = false)
	@JsonProperty("tipo_lancamento")
	private int tipoLancamento;

	@Column(name = "descricao", nullable = false, length = 9999)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "mensal")
	private boolean mensal;

	@Column(name = "pago")
	private boolean pago;

	@Column(name = "tipo_pagamento", nullable = false)
	@JsonProperty("tipo_pagamento")
	private int tipoPagamento;

	@Column(name = "quantidade_parcelas")
	@JsonProperty("quantidade_parcelas")
	private int quantidadeParcelas;

	@Column(name = "numero_parcela")
	@JsonProperty("numero_parcela")
	private int numeroParcela;

	@Column(name = "mes_referencia", nullable = false)
	@JsonProperty("mes_referencia")
	private int mesReferencia;

	@Column(name = "ano_referencia", nullable = false)
	@JsonProperty("ano_referencia")
	private int anoReferencia;

	@Column(name = "data_lancamento", nullable = false)
	@JsonProperty("data_lancamento")
	private LocalDateTime dataLancamento;

	@Column(name = "data_vencimento")
	@JsonProperty("data_vencimento")
	private LocalDateTime dataVencimento;

	@Column(name = "data_prevista_pagamento")
	@JsonProperty("data_prevista_pagamento")
	private LocalDateTime dataPrevistaPagamento;

	@Column(name = "data_pagamento")
	@JsonProperty("data_pagamento")
	private LocalDateTime dataPagamento;

	@Column(name = "observacao", length = 9999)
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(int tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isMensal() {
		return mensal;
	}

	public void setMensal(boolean mensal) {
		this.mensal = mensal;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public int getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(int tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public int getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(int mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public int getAnoReferencia() {
		return anoReferencia;
	}

	public void setAnoReferencia(int anoReferencia) {
		this.anoReferencia = anoReferencia;
	}

	public LocalDateTime getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDateTime dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDateTime getDataPrevistaPagamento() {
		return dataPrevistaPagamento;
	}

	public void setDataPrevistaPagamento(LocalDateTime dataPrevistaPagamento) {
		this.dataPrevistaPagamento = dataPrevistaPagamento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	

}
