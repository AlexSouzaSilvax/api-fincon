package com.fincon.enums;

public enum TipoLancamento {

	ENTRADA(0, "Entrada"), SAIDA(1, "Saída");

	private int value;
	private String descricao;

	TipoLancamento(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}
}
