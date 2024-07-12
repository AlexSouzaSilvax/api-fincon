package com.fincon.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoPagamento {

	DINHEIRO(0, "Dinheiro"), PIX(1, "Pix"), DEBITO(2, "Débito"), CREDITO(3, "Crédito"), BOLETO(4, "Boleto"),
	TED(5, "Transferência");

	@SuppressWarnings("unused")
	private int value;
	private String descricao;

	TipoPagamento(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	private static final Map<String, TipoPagamento> tipoPagamentoPorDescricao = new HashMap<>();

	static {
		for (TipoPagamento tP : TipoPagamento.values()) {
			tipoPagamentoPorDescricao.put(tP.getDescricao(), tP);
		}
	}

	public static TipoPagamento findTipoPagamentoByDescricao(String pDescricao) {
		return tipoPagamentoPorDescricao.get(pDescricao);
	}
}
