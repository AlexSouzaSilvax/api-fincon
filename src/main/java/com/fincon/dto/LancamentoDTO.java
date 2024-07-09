package com.fincon.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;

import lombok.Data;

@Data
public class LancamentoDTO {

    private UUID id;

    private String categoria;

    private String descricao;

    private BigDecimal valor;

    @Column(name = "tipo_pagamento")
    @JsonProperty("tipo_pagamento")
    private String tipoPagamento;

    @Column(name = "tipo_lancamento")
    @JsonProperty("tipo_lancamento")
    private String tipoLancamento;

    @Column(name = "data_lancamento")
    @JsonProperty("data_lancamento")
    private Date dataLancamento;

    private boolean pago;

    private boolean mensal;
    @Column(name = "mes_referencia", nullable = false)
    @JsonProperty("mes_referencia")
    private int mesReferencia;

    @Column(name = "ano_referencia", nullable = false)
    @JsonProperty("ano_referencia")
    private int anoReferencia;

    @Column(name = "observacao", length = 9999)
    private String observacao;

    @Column(name = "data_vencimento")
    @JsonProperty("data_vencimento")
    private Date dataVencimento;

    @Column(name = "data_prevista_pagamento")
    @JsonProperty("data_prevista_pagamento")
    private Date dataPrevistaPagamento;

    @Column(name = "quantidade_parcelas")
    @JsonProperty("quantidade_parcelas")
    private int quantidadeParcelas;

    private UUID usuario;

    public LancamentoDTO() {

    }

    public LancamentoDTO(Lancamento pLancamento) {
        this.id = pLancamento.getId();
        Categoria c = pLancamento.getCategoria();
        this.categoria = c.getDescricao();
        this.descricao = pLancamento.getDescricao();
        this.valor = pLancamento.getValor();
        TipoPagamento tP = pLancamento.getTipoPagamento();
        this.tipoPagamento = tP.getDescricao();
        TipoLancamento tL = pLancamento.getTipoLancamento();
        this.tipoLancamento = tL.getDescricao();
        this.dataLancamento = pLancamento.getDataLancamento();
        this.pago = pLancamento.isPago();
        this.mensal = pLancamento.isMensal();
        this.mesReferencia = pLancamento.getMesReferencia();
        this.anoReferencia = pLancamento.getAnoReferencia();
        this.observacao = pLancamento.getObservacao();
        this.dataVencimento = pLancamento.getDataVencimento();
        this.dataPrevistaPagamento = pLancamento.getDataPrevistaPagamento();
        this.quantidadeParcelas = pLancamento.getQuantidadeParcelas();
        this.usuario = pLancamento.getUser().getId();
    }
}