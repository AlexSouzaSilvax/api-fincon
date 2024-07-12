package com.fincon.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fincon.Util.Util;
import com.fincon.dto.LancamentoDTO;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;
import com.fincon.model.User;
import com.fincon.repository.LancamentoRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LancamentoService {

	private LancamentoRepository lancamentoRespository;

	private LancamentoDTO lancamentoDTO;

	public List<LancamentoDTO> findAll() {
		return lancamentoDTO
				.LancamentoToLancamentoDTO(lancamentoRespository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	public List<Lancamento> findAllOrderNumeroParcela() {
		return lancamentoRespository.findAllOrderNumeroParcela();
	}

	public List<LancamentoDTO> findListMain(UUID idUser, int pMesReferencia, int pAnoReferencia) {
		List<Lancamento> listaLancamentos = new ArrayList<>();
		List<LancamentoDTO> listaLancamentoDTO = new ArrayList<>();

		if (pMesReferencia == 0) {
			listaLancamentos = lancamentoRespository.findListMain(idUser, pAnoReferencia);
		} else {
			this.insereSaldoMesAnterior(idUser, pMesReferencia, pAnoReferencia);
			listaLancamentos = lancamentoRespository.findListMain(idUser, pMesReferencia,
					pAnoReferencia);
		}

		for (Lancamento pLancamento : listaLancamentos) {
			listaLancamentoDTO.add(new LancamentoDTO(pLancamento));
		}

		return listaLancamentoDTO;
	}

	public List<LancamentoDTO> findListMain(UUID idUser, int pAnoReferencia) {
		List<LancamentoDTO> listaLancamentoDTO = new ArrayList<>();
		for (Lancamento pLancamento : lancamentoRespository.findListMain(idUser, pAnoReferencia)) {
			listaLancamentoDTO.add(new LancamentoDTO(pLancamento));
		}
		return listaLancamentoDTO;
	}

	@Transactional
	public void insereSaldoMesAnterior(UUID idUser, int pMesReferencia, int pAnoReferencia) {
		int mesSeguinte = pMesReferencia + 1;
		if (mesSeguinte > 12) {
			pMesReferencia = 1;
			pAnoReferencia += 1;
		}
		Date hoje = Util.dataAtual();
		// verifica se exite o lançamento saldoMesAnterior já criado
		List<Lancamento> listaLancamentos = this.lancamentoRespository.findByLancamentoSaldoMesAnterior(idUser,
				pMesReferencia, pAnoReferencia);
		if (listaLancamentos.size() == 0) { // nenhum lancamento criado
			// criar lancamento
			Lancamento novoLancamentoSaldoMesAnterior = new Lancamento();
			novoLancamentoSaldoMesAnterior.setUser(new User(idUser));
			novoLancamentoSaldoMesAnterior.setAnoReferencia(pAnoReferencia);
			novoLancamentoSaldoMesAnterior.setMesReferencia(pMesReferencia);
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
					.setValor(buscaTotalLancamentoPorMesAno(idUser, (pMesReferencia - 1), pAnoReferencia));
			save(idUser, novoLancamentoSaldoMesAnterior);
		}
		// System.out.println("Salvo com sucesso");
	}

	public Optional<Lancamento> findById(UUID id) {
		return lancamentoRespository.findById(id);
	}

	@Transactional
	public void delete(UUID id) {
		if (existsLancamento(id)) {
			lancamentoRespository.deleteById(id);
		}
	}

	@Transactional
	public Object saveOrUpdate(Lancamento pLancamento) {
		if (pLancamento.getId() != null) {
			return update(new LancamentoDTO(pLancamento));
		}
		return save(pLancamento.getUser().getId(), pLancamento);
	}

	@Transactional
	public Lancamento save(UUID idUser, Lancamento pLancamento) {
		pLancamento.setUser(new User(idUser));

		if (pLancamento.getId() == null) {
			pLancamento.setDataLancamento(Util.dataAtual());
		}

		if (pLancamento.isPago()) {
			pLancamento.setDataPagamento(Util.dataAtual());
		}

		// quando for mensal
		if (pLancamento.isMensal() && pLancamento.getTipoPagamento() != TipoPagamento.CREDITO) {
			// replicar para apenas 6 meses, NO CASO O ATUAL + 6 PRA FRENTE
			saveLancamentosProxMensal(6, pLancamento);
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

	@Transactional
	public Optional<Lancamento> update(LancamentoDTO pLancamento) {
		lancamentoRespository.updateAllById(pLancamento.getAnoReferencia(),
				pLancamento.getCategoria(),
				pLancamento.getDataPrevistaPagamento(),
				pLancamento.getDataVencimento(),
				pLancamento.getDescricao(),
				pLancamento.isMensal(),
				pLancamento.getMesReferencia(),
				pLancamento.getObservacao(),
				pLancamento.isPago(),
				pLancamento.getQuantidadeParcelas(),
				pLancamento.getTipoLancamento(),
				pLancamento.getTipoPagamento(),
				pLancamento.getValor(),
				pLancamento.getId());
		return findById(pLancamento.getId());
	}

	@Transactional
	private void saveLancamentosProxMensal(int pQuantidadedeMensal, Lancamento pLancamento) {
		int quantidadedeMensal = pQuantidadedeMensal;
		int novoAnoReferencia = pLancamento.getAnoReferencia();
		int novo = 1;
		if (quantidadedeMensal > 0) {
			int novoMesReferencia = pLancamento.getMesReferencia() + novo;
			for (int i = 0; i < quantidadedeMensal; i++) {
				Lancamento lancamento = manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia);
				lancamento.setPago(false);
				lancamentoRespository.save(lancamento);
				novo++;
				novoMesReferencia++;
				if (novoMesReferencia > 12) {
					novoAnoReferencia++;
					novo = 1;
					novoMesReferencia = 1;
				}
			}
		}
	}

	@Transactional
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

	@Transactional
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

	@Transactional
	@SuppressWarnings("deprecation")
	private Lancamento manipulaDadosLancamento(Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = new Lancamento();
		// trata mes fevereiro
		if (novoMesReferencia == 2 && ultimoDiaMes(novoMesReferencia) == 28) {
			lancamento.setDataVencimento(
					Util.LocalDateTimeForDate(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
							pLancamento.getDataPrevistaPagamento().getHours(),
							pLancamento.getDataPrevistaPagamento().getMinutes())));

			lancamento.setDataPrevistaPagamento(
					Util.LocalDateTimeForDate(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
							pLancamento.getDataPrevistaPagamento().getHours(),
							pLancamento.getDataPrevistaPagamento().getMinutes())));

		} else {
			if (pLancamento.getDataVencimento() != null) {
				lancamento.setDataVencimento(
						Util.LocalDateTimeForDate(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
								pLancamento.getDataVencimento().getDay(), pLancamento.getDataVencimento().getHours(),
								pLancamento.getDataVencimento().getMinutes())));
			}
			if (pLancamento.getDataPrevistaPagamento() != null) {
				lancamento.setDataPrevistaPagamento(
						Util.LocalDateTimeForDate(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
								pLancamento.getDataPrevistaPagamento().getDay(),
								pLancamento.getDataPrevistaPagamento().getHours(),
								pLancamento.getDataPrevistaPagamento().getMinutes())));
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
		lancamento.setObservacao("Criado de forma automática pelo sistema.");
		lancamento.setUser(pLancamento.getUser());
		return lancamento;
	}

	@Transactional
	// salva lancamentos parcela
	private void saveLancamentoParcelas(int i, Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia);
		lancamento.setPago(false);
		lancamento.setDescricao(i + "/" + pLancamento.getQuantidadeParcelas() + " " + pLancamento.getDescricao());
		lancamento.setNumeroParcela(i);
		lancamentoRespository.save(lancamento);
	}

	@Transactional
	private BigDecimal buscaTotalLancamentoPorMesAno(UUID idUser, int pMesReferencia, int pAnoReferencia) {
		BigDecimal saldo = new BigDecimal(0);
		try {
			saldo = new BigDecimal(
					this.lancamentoRespository.buscaTotalLancamentoPorMes(idUser, pMesReferencia, pAnoReferencia));
		} catch (Exception e) {
			// System.out.println("Saldo do mes anterior vazio.");
		}
		return saldo;
	}

	@Transactional
	@SuppressWarnings("deprecation")
	private int ultimoDiaMes(int pMesReferencia) {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, (pMesReferencia - 1));
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		// ultimo dia do mes
		return instance.getTime().getDate();
	}

	@Transactional
	public boolean existsLancamento(UUID id) {
		return lancamentoRespository.existsById(id);
	}
}