package com.fincon.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.dto.LancamentoDTO;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;

import lombok.Data;

@Data
@Entity
@Table(name = "Lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne()
	@JoinColumn(name = "id_usuario", nullable = false)
	private User user;

	@Column(name = "tipo_lancamento", nullable = false)
	@JsonProperty("tipo_lancamento")
	private TipoLancamento tipoLancamento;

	@Column(name = "categoria", nullable = false, length = 9999)
	private Categoria categoria;

	@Column(name = "descricao", length = 9999)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "mensal")
	private boolean mensal;

	@Column(name = "pago")
	private boolean pago;

	@Column(name = "tipo_pagamento", nullable = false)
	@JsonProperty("tipo_pagamento")
	private TipoPagamento tipoPagamento;

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
	private Date dataLancamento;

	@Column(name = "data_vencimento")
	@JsonProperty("data_vencimento")
	private Date dataVencimento;

	@Column(name = "data_prevista_pagamento", nullable = false)
	@JsonProperty("data_prevista_pagamento")
	private Date dataPrevistaPagamento;

	@Column(name = "data_pagamento")
	@JsonProperty("data_pagamento")
	private Date dataPagamento;

	@Column(name = "observacao", length = 9999)
	private String observacao;

	public Lancamento() {

	}

	public Lancamento(LancamentoDTO pLancamentoDTO) {
		this.id = pLancamentoDTO.getId();
		System.out.println(Categoria.findCategoriaByDescricao(pLancamentoDTO.getCategoria()));
		this.categoria = Categoria.findCategoriaByDescricao(pLancamentoDTO.getCategoria());
		this.descricao = pLancamentoDTO.getDescricao();
		this.valor = pLancamentoDTO.getValor();
		this.tipoPagamento = TipoPagamento.findTipoPagamentoByDescricao(pLancamentoDTO.getTipoPagamento());
		this.tipoLancamento = TipoLancamento.findTipoLancamentoByDescricao(pLancamentoDTO.getTipoLancamento());
		this.dataLancamento = pLancamentoDTO.getDataLancamento();
		this.pago = pLancamentoDTO.isPago();
		this.mensal = pLancamentoDTO.isMensal();
		this.mesReferencia = pLancamentoDTO.getMesReferencia();
		this.anoReferencia = pLancamentoDTO.getAnoReferencia();
		this.observacao = pLancamentoDTO.getObservacao();
		this.dataVencimento = pLancamentoDTO.getDataVencimento();
		this.dataPrevistaPagamento = pLancamentoDTO.getDataPrevistaPagamento();
		this.quantidadeParcelas = pLancamentoDTO.getQuantidadeParcelas();
		this.user = new User(pLancamentoDTO.getUsuario());
	}
}
