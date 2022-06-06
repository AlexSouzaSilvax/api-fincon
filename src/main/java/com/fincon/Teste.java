package com.fincon;

import java.time.LocalDateTime;

import com.fincon.model.Lancamento;

public class Teste {
    public static void main(String[] args) {

        Lancamento pLancamento = new Lancamento();

        pLancamento.setQuantidadeParcelas(8);
        pLancamento.setMesReferencia(6);

        int novo = 1;

        for (int i = 0; i < pLancamento.getQuantidadeParcelas(); i++) {
            int novoMesReferencia = pLancamento.getMesReferencia() + novo;
            if (novoMesReferencia <= 12) {
                System.out.println("DATA " + LocalDateTime.of(2022, novoMesReferencia, 01, 00, 00, 00));
                novo++;
            }
        }

        System.out.println(novo);



    }

}
