package com.fincon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;

import lombok.Data;

@Data
public class LancamentoDTO {

    private Long id;

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
    private LocalDateTime dataLancamento;

    private boolean pago;

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
    }
}