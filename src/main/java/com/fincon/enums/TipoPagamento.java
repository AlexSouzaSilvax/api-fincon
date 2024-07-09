package com.fincon.enums;

public enum TipoPagamento {

	DINHEIRO(0, "Dinheiro"), PIX(1, "Pix"), DEBITO(2, "Débito"), CREDITO(3, "Crédito"), BOLETO(4, "Boleto"), TED(5, "Transferência");

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
}
