package com.fincon.model;

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

	@Column(name = "descricao", nullable = false)
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

	@Column(name = "data_prevista_pagamento", nullable = false)
	@JsonProperty("data_prevista_pagamento")
	private LocalDateTime dataPrevistaPagamento;

	@Column(name = "data_pagamento")
	@JsonProperty("data_pagamento")
	private LocalDateTime dataPagamento;

	@Column(name = "observacao")
	private String observacao;

}
