package com.fincon.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fincon.model.Lancamento;
import com.fincon.repository.LancamentoRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LancamentoService {

	private LancamentoRepository lancamentoRespository;

	public List<Lancamento> findAll() {
		return lancamentoRespository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	public Optional<Lancamento> findById(Long id) {
		return lancamentoRespository.findById(id);
	}

	public Lancamento save(Lancamento pLancamento) {

		if (pLancamento.getId() == null) {
			pLancamento.setDataLancamento(LocalDateTime.now().minusDays(1).minusMonths(1).minusHours(2));
		}

		if (pLancamento.isPago()) {
			pLancamento.setDataPagamento(LocalDateTime.now().minusDays(1).minusMonths(1).minusHours(2));
		}

		// quando for mensal
		if (pLancamento.isMensal() && pLancamento.getTipoPagamento() != 3) {
			System.out.println("é mensal");
			// em andamento
		}

		// quando for parcelado
		if (pLancamento.getQuantidadeParcelas() > 1) {

			// criando proximas parcelas
			System.out.println("\n<----- INICIO ----->\n");
			System.out.println(
					"Mês/Ano referência: " + pLancamento.getMesReferencia() + "/" + pLancamento.getAnoReferencia());
			System.out.println("Quantidade de parcelas: " + pLancamento.getQuantidadeParcelas());

			// salva parcelas do ano atual
			int quantidadeParcelasAnoAtual = saveLancamentoParcelasAnoAtual(pLancamento);

			// verifica quantos anos de parcelas						
			// salva parcelas por ano
			//saveParcelasPorAno();
			int quantidadedeParcelasProximoAno = pLancamento.getQuantidadeParcelas() - quantidadeParcelasAnoAtual;				
			int numeroParcela = quantidadeParcelasAnoAtual+1;
			int novoAnoReferencia = pLancamento.getAnoReferencia() + 1;
			int novo = 1;					
			if(quantidadedeParcelasProximoAno > 0) {
				for(int i = 0; i < quantidadedeParcelasProximoAno; i++) {
					int novoMesReferencia = 0 + novo;	
					saveLancamentoParcelas(numeroParcela, pLancamento, novoMesReferencia, novoAnoReferencia);
					novo++;
					numeroParcela++;					
					if(novoMesReferencia == 12) {
						novoAnoReferencia++;
						novo = 1;
					}
				}				
			}

			System.out.println("\n<----- FIM ----->\n");

			pLancamento.setNumeroParcela(1);
			pLancamento.setDescricao(pLancamento.getDescricao() + " " + 1 + "/" + pLancamento.getQuantidadeParcelas());
		}

		return lancamentoRespository.save(pLancamento);
	}

	public void delete(Long id) {
		lancamentoRespository.deleteById(id);
	}
	
	public void saveParcelasPorAno() {
		/*
		int quantidadedeParcelasProximoAno = pLancamento.getQuantidadeParcelas();
		int quantidadeAnosParcela = quantidadedeParcelasProximoAno / 12;
		System.out.println("Quantidade de anos de parcela: " + quantidadeAnosParcela);
		int numeroParcela = quantidadeParcelasAnoAtual;
		int novoAnoReferencia = pLancamento.getAnoReferencia() + 1;
		int novo = 1;
		
		for (int i = 0; i < quantidadeAnosParcela; i++) {

			for (int j = 0; j < 12; j++) {
				int novoMesReferencia = 0 + novo;
				System.out.println("\n<----- " + novoAnoReferencia + " ----->");
				saveLancamentoParcelas(numeroParcela, pLancamento, novoMesReferencia, novoAnoReferencia);
				novo++;
				numeroParcela++;
			}
			novoAnoReferencia++;
			quantidadedeParcelasProximoAno = quantidadedeParcelasProximoAno - novo;
			System.out.println("Quantidade de parcelas para o próximo ano: " + quantidadedeParcelasProximoAno);
			System.out.println("<----- " + novoAnoReferencia + " ----->\n");

		}
		*/
	}
	
	// retorna quantidade de parcelas do ano atual já salvas
	public int saveLancamentoParcelasAnoAtual(Lancamento pLancamento) {
		int pQuantidadeParcelas = pLancamento.getQuantidadeParcelas();
		int pMesReferencia = pLancamento.getMesReferencia();
		int pAnoReferencia = pLancamento.getAnoReferencia();
		int novo = 1;
		for (int i = 2; i < (pQuantidadeParcelas + 1); i++) {
			int novoMesReferencia = pMesReferencia + novo;
			if (novoMesReferencia <= 12) {
				saveLancamentoParcelas(i, pLancamento, novoMesReferencia, pAnoReferencia);
				novo++;
			}
		}
		return novo;
	}
	

	// salva lancamentos parcela
	public void saveLancamentoParcelas(int i, Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = new Lancamento();
		// trata mes fevereiro
		if (novoMesReferencia == 2) {
			if (pLancamento.getDataVencimento().getDayOfMonth() >= 29
					|| pLancamento.getDataPrevistaPagamento().getDayOfMonth() >= 29) {
				lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
						pLancamento.getDataPrevistaPagamento().getHour(),
						pLancamento.getDataPrevistaPagamento().getMinute()));
				lancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
						pLancamento.getDataPrevistaPagamento().getHour(),
						pLancamento.getDataPrevistaPagamento().getMinute()));
			}
		} else {
			lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
					pLancamento.getDataVencimento().getDayOfMonth(), pLancamento.getDataVencimento().getHour(),
					pLancamento.getDataVencimento().getMinute()));
			lancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
					pLancamento.getDataPrevistaPagamento().getDayOfMonth(),
					pLancamento.getDataPrevistaPagamento().getHour(),
					pLancamento.getDataPrevistaPagamento().getMinute()));
		}
		lancamento.setMesReferencia(novoMesReferencia);
		lancamento.setAnoReferencia(novoAnoReferencia);
		lancamento.setTipoLancamento(pLancamento.getTipoLancamento());
		lancamento.setValor(pLancamento.getValor());
		lancamento.setMensal(pLancamento.isMensal());
		lancamento.setPago(pLancamento.isPago());
		lancamento.setTipoPagamento(pLancamento.getTipoPagamento());
		lancamento.setQuantidadeParcelas(pLancamento.getQuantidadeParcelas());
		lancamento.setDataLancamento(pLancamento.getDataLancamento());
		lancamento.setDescricao(pLancamento.getDescricao() + " " + i + "/" + pLancamento.getQuantidadeParcelas());
		lancamento.setNumeroParcela(i);
		lancamento.setObservacao(pLancamento.getObservacao());
		lancamentoRespository.save(lancamento);
	}

}