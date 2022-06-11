package com.fincon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;

import lombok.Data;

@Data
public class LancamentoDTO {

    private Long id;
    
    private String descricao;
    
    private BigDecimal valor;

    @Column(name = "tipo_pagamento")
	@JsonProperty("tipo_pagamento")
	private TipoPagamento tipoPagamento;

    @Column(name = "tipo_lancamento")
	@JsonProperty("tipo_lancamento")
	private TipoLancamento tipoLancamento;
    
    @Column(name = "data_lancamento")
    @JsonProperty("data_lancamento")
	private LocalDateTime dataLancamento;

    private boolean pago;

    public LancamentoDTO(Lancamento pLancamento) {
        this.id = pLancamento.getId();
        this.descricao = pLancamento.getDescricao();
        this.valor = pLancamento.getValor();
        this.tipoPagamento = pLancamento.getTipoPagamento();
        this.tipoLancamento = pLancamento.getTipoLancamento();
        this.dataLancamento = pLancamento.getDataLancamento();
        this.pago = pLancamento.isPago();
    }
}