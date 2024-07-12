package com.fincon.enums;

import java.util.HashMap;
import java.util.Map;

public enum Categoria {

    ALIMENTACAO(0, "Alimentação"),
    ASSINATURAS_SERVICOS(1, "Assinaturas/Serviços"),
    BARES_RESTAURANTES(2, "Bares/Restaurantes"),
    CASA(3, "Casa"),
    COMPRAS(4, "Compras"),
    CUIDADOS_PESSOAIS(5, "Cuidados Pessoais"),
    DIVIDAS_EMPRESTIMOS(6, "Dívidas/Emprestimos"),
    EDUCACAO(7, "Educação"),
    FAMILIA(8, "Família"),
    TAXAS_IMPOSTOS(9, "Impostos/Taxas"),
    INVESTIMENTOS(10, "Investimentos"),
    LAZER(11, "Lazer"),
    MERCADO(12, "Mercado"),
    OUTROS(13, "Outros"),
    PRESENTES_DOACOES(14, "Presentes/Doações"),
    ROUPAS(15, "Roupas"),
    SAUDE(16, "Saúde"),
    TRABALHO(17, "Trabalho"),
    TRANSPORTE(18, "Transporte"),
    VIAGEM(19, "Viagem"),
    COMBUSTIVEL(20, "Combustível"),
    POUPANCA(21, "Poupança");

    @SuppressWarnings("unused")
    private int value;
    private String descricao;

    Categoria(int value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    private static final Map<String, Categoria> categoriaPorDescricao = new HashMap<>();

    static {
        for (Categoria c : Categoria.values()) {
            categoriaPorDescricao.put(c.getDescricao(), c);
        }
    }

    public static Categoria findCategoriaByDescricao(String pDescricao) {
        return categoriaPorDescricao.get(pDescricao);
    }
}
