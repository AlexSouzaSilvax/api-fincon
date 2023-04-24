package com.fincon.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fincon.Util.Util;
import com.fincon.dto.LancamentoDTO;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;
import com.fincon.model.Usuario;
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

	public List<Lancamento> findAllOrderNumeroParcela() {
		return lancamentoRespository.findAllOrderNumeroParcela();
	}

	public List<LancamentoDTO> findListMain(long idUsuario, int pMesReferencia, int pAnoReferencia) {
		List<Lancamento> listaLancamentos = new ArrayList<>();
		List<LancamentoDTO> listaLancamentoDTO = new ArrayList<>();

		if (pMesReferencia == 0) {
			listaLancamentos = lancamentoRespository.findListMain(idUsuario, pAnoReferencia);
		} else {
			this.insereSaldoMesSeguinte(idUsuario, pMesReferencia, pAnoReferencia);
			listaLancamentos = lancamentoRespository.findListMain(idUsuario, pMesReferencia,
					pAnoReferencia);
		}

		for (Lancamento pLancamento : listaLancamentos) {
			listaLancamentoDTO.add(new LancamentoDTO(pLancamento));
		}

		return listaLancamentoDTO;
	}

	public List<LancamentoDTO> findListMain(long idUsuario, int pAnoReferencia) {
		List<LancamentoDTO> listaLancamentoDTO = new ArrayList<>();
		for (Lancamento pLancamento : lancamentoRespository.findListMain(idUsuario, pAnoReferencia)) {
			listaLancamentoDTO.add(new LancamentoDTO(pLancamento));
		}
		return listaLancamentoDTO;
	}

	public void insereSaldoMesSeguinte(long idUsuario, int pMesReferencia, int pAnoReferencia) {
		// System.out.println("\n\n");
		// Trata caso seja novo ano
		int mesSeguinte = pMesReferencia + 1;
		if (mesSeguinte > 12) {
			// System.out.println("Caiu aqui");
			mesSeguinte = 1;
			pAnoReferencia += 1;
		}
		// ultimo dia do mes
		int ultimoDiaMes = ultimoDiaMes(pMesReferencia);
		LocalDateTime hoje = Util.dataAtual();
		// verifica se hoje é o ultimo dia do mes
		if (ultimoDiaMes == hoje.getDayOfMonth()) {
			// verifica se exite o lançamento saldoMesAnterior já criado
			List<Lancamento> listaLancamentos = this.lancamentoRespository.findByLancamentoSaldoMesAnterior(idUsuario,
					mesSeguinte, pAnoReferencia);
			if (listaLancamentos.isEmpty()) {
				// criar lancamento
				try {
					Lancamento novoLancamentoSaldoMesAnterior = new Lancamento();
					novoLancamentoSaldoMesAnterior.setUsuario(new Usuario(idUsuario));
					novoLancamentoSaldoMesAnterior.setAnoReferencia(pAnoReferencia);
					novoLancamentoSaldoMesAnterior.setMesReferencia(mesSeguinte);
					novoLancamentoSaldoMesAnterior.setCategoria(Categoria.TRABALHO);
					novoLancamentoSaldoMesAnterior.setDataPagamento(hoje);
					novoLancamentoSaldoMesAnterior.setDataPrevistaPagamento(hoje);
					novoLancamentoSaldoMesAnterior.setDataVencimento(hoje);
					novoLancamentoSaldoMesAnterior.setDescricao("Saldo do Mês Anterior");
					novoLancamentoSaldoMesAnterior.setObservacao("Criado de forma automática pelo sistema.");
					novoLancamentoSaldoMesAnterior.setPago(true);
					novoLancamentoSaldoMesAnterior.setTipoLancamento(TipoLancamento.ENTRADA);
					novoLancamentoSaldoMesAnterior.setTipoPagamento(TipoPagamento.PIX);
					novoLancamentoSaldoMesAnterior
							.setValor(buscaTotalLancamentoPorMesAno(idUsuario, pMesReferencia, pAnoReferencia));
					save(idUsuario, novoLancamentoSaldoMesAnterior);
				} catch (Exception e) {
					// System.out.println("Erro ao salvar: " + e);
				}
				// System.out.println("Salvo com sucesso");
			} else {
				// System.out.println("Já existe");
			}
		}
	}

	public Object findById(Long id) {
		return lancamentoRespository.findById(id);
	}

	public void delete(Long id) {
		lancamentoRespository.deleteById(id);
	}

	public Lancamento save(Long idUsuario, Lancamento pLancamento) {
		pLancamento.setUsuario(new Usuario(idUsuario));

		if (pLancamento.getId() == null) {
			pLancamento.setDataLancamento(Util.dataAtual());
		}

		if (pLancamento.isPago()) {
			pLancamento.setDataPagamento(Util.dataAtual());
		}

		// quando for mensal
		if (pLancamento.isMensal() && pLancamento.getTipoPagamento() != TipoPagamento.CREDITO) {
			// replicar para apenas 6 meses, NO CASO O ATUAL + 6 PRA FRENTE
			saveLancamentosProxMensal(5, pLancamento);
		}

		// quando for parcelado
		if (pLancamento.getQuantidadeParcelas() > 1) {
			// criando proximas parcelas
			// salva parcelas do ano atual
			int quantidadeParcelasAnoAtual = saveLancamentoParcelasAnoAtual(pLancamento);
			// salva restante das parcelas
			salvaParcelasDoProxAnoDiante(quantidadeParcelasAnoAtual, pLancamento);

			pLancamento.setNumeroParcela(1);
			pLancamento.setDescricao(pLancamento.getDescricao() + " " + 1 + "/" + pLancamento.getQuantidadeParcelas());
		}

		return lancamentoRespository.save(pLancamento);
	}

	public void update(LancamentoDTO pLancamento) {
		lancamentoRespository.updateAllById(pLancamento.getAnoReferencia(),
				Integer.valueOf(pLancamento.getCategoria()),
				pLancamento.getDataPrevistaPagamento(),
				pLancamento.getDataVencimento(),
				pLancamento.getDescricao(),
				pLancamento.isMensal(),
				pLancamento.getMesReferencia(),
				pLancamento.getObservacao(),
				pLancamento.isPago(),
				pLancamento.getQuantidadeParcelas(),
				Integer.valueOf(pLancamento.getTipoLancamento()),
				Integer.valueOf(pLancamento.getTipoPagamento()),
				pLancamento.getValor(),
				pLancamento.getId());
	}

	private void saveLancamentosProxMensal(int pQuantidadedeMensal, Lancamento pLancamento) {
		int quantidadedeMensal = pQuantidadedeMensal;
		int novoAnoReferencia = pLancamento.getAnoReferencia();
		int novo = 1;
		if (quantidadedeMensal > 0) {
			for (int i = 0; i < quantidadedeMensal; i++) {
				int novoMesReferencia = pLancamento.getMesReferencia() + novo;
				lancamentoRespository.save(manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia));
				novo++;
				novoMesReferencia++;
				if (novoMesReferencia == 12) {
					novoAnoReferencia++;
					novo = 1;
				}
			}
		}
	}

	private void salvaParcelasDoProxAnoDiante(int quantidadeParcelasAnoAtual, Lancamento pLancamento) {
		int quantidadedeParcelasProximoAno = pLancamento.getQuantidadeParcelas() - quantidadeParcelasAnoAtual;
		int numeroParcela = quantidadeParcelasAnoAtual + 1;
		int novoAnoReferencia = pLancamento.getAnoReferencia() + 1;
		int novo = 1;
		if (quantidadedeParcelasProximoAno > 0) {
			for (int i = 0; i < quantidadedeParcelasProximoAno; i++) {
				int novoMesReferencia = 0 + novo;
				saveLancamentoParcelas(numeroParcela, pLancamento, novoMesReferencia, novoAnoReferencia);
				novo++;
				numeroParcela++;
				if (novoMesReferencia == 12) {
					novoAnoReferencia++;
					novo = 1;
				}
			}
		}

	}

	// retorna quantidade de parcelas do ano atual já salvas
	private int saveLancamentoParcelasAnoAtual(Lancamento pLancamento) {
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

	private Lancamento manipulaDadosLancamento(Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = new Lancamento();
		// trata mes fevereiro
		if (novoMesReferencia == 2 && ultimoDiaMes(novoMesReferencia) == 28) {
			lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
					pLancamento.getDataPrevistaPagamento().getHour(),
					pLancamento.getDataPrevistaPagamento().getMinute()));

			lancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
					pLancamento.getDataPrevistaPagamento().getHour(),
					pLancamento.getDataPrevistaPagamento().getMinute()));

		} else {
			if (pLancamento.getDataVencimento() != null) {
				lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
						pLancamento.getDataVencimento().getDayOfMonth(), pLancamento.getDataVencimento().getHour(),
						pLancamento.getDataVencimento().getMinute()));
			}
			if (pLancamento.getDataPrevistaPagamento() != null) {
				lancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
						pLancamento.getDataPrevistaPagamento().getDayOfMonth(),
						pLancamento.getDataPrevistaPagamento().getHour(),
						pLancamento.getDataPrevistaPagamento().getMinute()));
			}
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
		lancamento.setDescricao(pLancamento.getDescricao());
		lancamento.setCategoria(pLancamento.getCategoria());
		lancamento.setObservacao(pLancamento.getObservacao());
		lancamento.setUsuario(pLancamento.getUsuario());
		return lancamento;
	}

	// salva lancamentos parcela
	private void saveLancamentoParcelas(int i, Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia);
		lancamento.setDescricao(pLancamento.getDescricao() + " " + i + "/" + pLancamento.getQuantidadeParcelas());
		lancamento.setNumeroParcela(i);
		lancamentoRespository.save(lancamento);
	}

	private BigDecimal buscaTotalLancamentoPorMesAno(long idUsuario, int pMesReferencia, int pAnoReferencia) {
		BigDecimal saldo = new BigDecimal(0);
		try {
			saldo = new BigDecimal(
					this.lancamentoRespository.buscaTotalLancamentoPorMes(idUsuario, pMesReferencia, pAnoReferencia));
		} catch (Exception e) {
			// System.out.println("Saldo do mes anterior vazio.");
		}
		return saldo;
	}

	private int ultimoDiaMes(int pMesReferencia) {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, (pMesReferencia - 1));
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		// ultimo dia do mes
		return instance.getTime().getDate();
	}
}