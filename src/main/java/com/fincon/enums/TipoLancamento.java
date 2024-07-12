package com.fincon.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoLancamento {

	ENTRADA(0, "Entrada"), SAIDA(1, "Saída");

	@SuppressWarnings("unused")
	private int value;
	private String descricao;

	TipoLancamento(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	private static final Map<String, TipoLancamento> tipoLancamentoPorDescricao = new HashMap<>();

	static {
		for (TipoLancamento tpL : TipoLancamento.values()) {
			tipoLancamentoPorDescricao.put(tpL.getDescricao(), tpL);
		}
	}

	public static TipoLancamento findTipoLancamentoByDescricao(String pDescricao) {
		return tipoLancamentoPorDescricao.get(pDescricao);
	}
}
