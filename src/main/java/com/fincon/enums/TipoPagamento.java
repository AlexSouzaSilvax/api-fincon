package com.fincon.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoPagamento {

	DINHEIRO(0, "Dinheiro"), PIX(1, "Pix"), DEBITO(2, "Débito"), CREDITO(3, "Crédito"), BOLETO(4, "Boleto"),
	TED(5, "Transferência");

	private int value;
	private String descricao;

	TipoPagamento(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public int getValue() {
		return this.value;
	}

	private static final Map<String, TipoPagamento> tipoPagamentoPorDescricao = new HashMap<>();

	static {
		for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
			tipoPagamentoPorDescricao.put(tipoPagamento.getDescricao(), tipoPagamento);
		}
	}

	public static TipoPagamento findTipoPagamentoByDescricao(String pDescricao) {
		return tipoPagamentoPorDescricao.get(pDescricao);
	}

	public static TipoPagamento findTipoPagamentoByValue(int pValue) {
		for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
			if (tipoPagamento.getValue() == pValue) {
				return tipoPagamento;
			}
		}
		return null;
	}
}
