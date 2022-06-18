package com.fincon.enums;

public enum Categoria {

    ALIMENTACAO(1, "Alimentação"),
    ASSINATURAS_SERVICOS(2, "Assinaturas/Serviços"),
    BARES_RESTAURANTES(3, "Bares/Restaurantes"),
    CASA(4, "Casa"),
    COMPRAS(5, "Compras"),
    CUIDADOS_PESSOAIS(6, "Cuidados Pessoais"),
    DIVIDAS_EMPRESTIMOS(7, "Dívidas/Emprestimos"),
    EDUCACAO(8, "Educação"),
    FAMILIA(9, "Família"),
    TAXAS_IMPOSTOS(10, "Impostos/Taxas"),
    INVESTIMENTOS(11, "Investimentos"),
    LAZER(12, "Lazer"),
    MERCADO(13, "Mercado"),
    OUTROS(14, "Outros"),
    PRESENTES_DOACOES(15, "Presentes/Doações"),
    ROUPAS(16, "Roupas"),
    SAUDE(17, "Saúde"),
    TRABALHO(18, "Trabalho"),
    TRANSPORTE(19, "Transporte"),
    VIAGEM(20, "Viagem"),
    COMBUSTIVEL(21, "Combustível"),
    POUPANCA(22, "Poupança");

    private int value;
    private String descricao;

    Categoria(int value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
