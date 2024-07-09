package com.fincon.enums;

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
}
