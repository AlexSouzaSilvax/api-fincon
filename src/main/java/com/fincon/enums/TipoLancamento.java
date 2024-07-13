package com.fincon.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoLancamento {

	ENTRADA(0, "Entrada"), SAIDA(1, "Saída");

	private int value;
	private String descricao;

	TipoLancamento(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public int getValue() {
		return this.value;
	}

	private static final Map<String, TipoLancamento> tipoLancamentoPorDescricao = new HashMap<>();

	static {
		for (TipoLancamento tipoLancamento : TipoLancamento.values()) {
			tipoLancamentoPorDescricao.put(tipoLancamento.getDescricao(), tipoLancamento);
		}
	}

	public static TipoLancamento findTipoLancamentoByDescricao(String pDescricao) {
		return tipoLancamentoPorDescricao.get(pDescricao);
	}

	public static TipoLancamento findTipoLancamentoByValue(int pValue) {
        for (TipoLancamento tipoLancamento : TipoLancamento.values()) {
            if (tipoLancamento.getValue() == pValue) {
                return tipoLancamento;
            }
        }
        return null;
    }
}
