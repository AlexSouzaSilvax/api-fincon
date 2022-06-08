package com.fincon;

import java.time.LocalDateTime;

import com.fincon.model.Lancamento;

public class Teste {
	public static void main(String[] args) {

		Lancamento pLancamento = new Lancamento();
		pLancamento.setDataVencimento(LocalDateTime.of(2022, 01, 29, 0, 0));
		pLancamento.setDataPrevistaPagamento(LocalDateTime.of(2022, 01, 29, 0, 0));

		int novoMesReferencia = 2;
		int novoAnoReferencia = 2022;

		if (novoMesReferencia == 2) {
			System.out.println("AQUI 1");
			if (pLancamento.getDataVencimento().getDayOfMonth() > 29) {
				System.out.println("AQUI 2");
				pLancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 29,
						pLancamento.getDataVencimento().getHour(), pLancamento.getDataVencimento().getMinute()));
				pLancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 29,
						pLancamento.getDataVencimento().getHour(), pLancamento.getDataVencimento().getMinute()));
			}
		} else {
			System.out.println("AQUI 3");
			pLancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
					pLancamento.getDataVencimento().getDayOfMonth(), pLancamento.getDataVencimento().getHour(),
					pLancamento.getDataVencimento().getMinute()));
			pLancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
					pLancamento.getDataPrevistaPagamento().getDayOfMonth(),
					pLancamento.getDataPrevistaPagamento().getHour(),
					pLancamento.getDataPrevistaPagamento().getMinute()));
		}
		System.out.println("AQUI 4");
		System.out.println("DATA VENCIMENTO = " + pLancamento.getDataVencimento());
		System.out.println("DATA PREVISTA PAGAMENTO = " + pLancamento.getDataPrevistaPagamento());

		/*
		 * pLancamento.setQuantidadeParcelas(31); pLancamento.setMesReferencia(6);
		 * pLancamento.setAnoReferencia(2022);
		 * 
		 * System.out.println("\n<----- INICIO ----->\n"); System.out.println(
		 * "Mês/Ano referência: " + pLancamento.getMesReferencia() + "/" +
		 * pLancamento.getAnoReferencia());
		 * System.out.println("Quantidade de parcelas: " +
		 * pLancamento.getQuantidadeParcelas());
		 * 
		 * System.out.println("\n<---------->"); int novo =
		 * teste(pLancamento.getQuantidadeParcelas(), pLancamento.getMesReferencia(),
		 * pLancamento.getAnoReferencia(), pLancamento);
		 * System.out.println("Quantidade de parcelas do ano " +
		 * pLancamento.getAnoReferencia() + " = " + novo);
		 * System.out.println("<---------->\n");
		 * 
		 * int novoAno = pLancamento.getAnoReferencia(); int
		 * quantidadedeParcelasProximoAno = pLancamento.getQuantidadeParcelas();
		 * 
		 * int quantidadeAnosParcela = quantidadedeParcelasProximoAno / 12;
		 * System.out.println("Quantidade de anos de parcela: " +
		 * quantidadeAnosParcela);
		 * 
		 * for (int i = 0; i < quantidadeAnosParcela; i++) { novoAno++;
		 * System.out.println("\n<----- " + novoAno + " ----->"); novo =
		 * teste(quantidadedeParcelasProximoAno, 0, novoAno, pLancamento);
		 * quantidadedeParcelasProximoAno = quantidadedeParcelasProximoAno - novo;
		 * System.out.println("Quantidade de parcelas para o próximo ano: " +
		 * quantidadedeParcelasProximoAno); System.out.println("<----- " + novo +
		 * " ----->\n"); }
		 * 
		 * if (quantidadedeParcelasProximoAno <= 12) { novoAno++;
		 * teste(quantidadedeParcelasProximoAno, 0, novoAno, pLancamento); }
		 * 
		 * System.out.println("\n<----- FIM ----->\n");
		 */
	}

	public static int teste(int pQuantidadeParcelas, int pMesReferencia, int pAnoReferencia, Lancamento pLancamento) {
		int novo = 1;

		for (int i = 0; i < pQuantidadeParcelas; i++) {
			int novoMesReferencia = pMesReferencia + novo;
			if (novoMesReferencia <= 12) {

				System.out.println("DATA " + LocalDateTime.of(pAnoReferencia, novoMesReferencia, 01, 00, 00, 00));
				// saveLancamentoParcelas(2, pLancamento, novoMesReferencia, pAnoReferencia);

				novo++;
			}
		}
		return (novo - 1);

	}
}
