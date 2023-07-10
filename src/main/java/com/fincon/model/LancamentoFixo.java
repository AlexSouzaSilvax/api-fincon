package com.fincon.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;

import lombok.Data;

@Data
@Entity
@Table(name = "LancamentoFixo")
public class LancamentoFixo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@Column(name = "tipo_lancamento", nullable = false)
	@JsonProperty("tipo_lancamento")
	private TipoLancamento tipoLancamento;

	@Column(name = "categoria", nullable = false, length = 9999)
	private Categoria categoria;

	@Column(name = "descricao", length = 9999)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "tipo_pagamento", nullable = false)
	@JsonProperty("tipo_pagamento")
	private TipoPagamento tipoPagamento;

	@Column(name = "data_lancamento", nullable = false)
	@JsonProperty("data_lancamento_fixo")
	private LocalDateTime dataLancamentoFixo;

	@Column(name = "dia_vencimento")
	@JsonProperty("dia_vencimento")
	private String diaVencimento;

	@Column(name = "dia_previsto_pagamento", nullable = false)
	@JsonProperty("dia_previsto_pagamento")
	private String diaPrevistoPagamento;

}
