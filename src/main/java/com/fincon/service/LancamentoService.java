package com.fincon.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.fincon.Util.Util;
import com.fincon.dto.LancamentoDTO;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.Lancamento;
import com.fincon.model.User;
import com.fincon.repository.LancamentoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LancamentoService {

	private final LancamentoRepository lancamentoRespository;

	private final LancamentoDTO lancamentoDTO;

	private final UsuarioService usuarioService;

	public List<LancamentoDTO> findAll() {
		return lancamentoDTO
				.LancamentoToLancamentoDTO(lancamentoRespository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	public List<Lancamento> findAllOrderNumeroParcela() {
		return lancamentoRespository.findAllOrderNumeroParcela();
	}

	@Transactional(rollbackFor = Exception.class)
	public List<LancamentoDTO> findListMain(UUID idUser, int pMesReferencia, int pAnoReferencia) {
		try {
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
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
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
		try {
			int mesSeguinte = pMesReferencia + 1;
			if (mesSeguinte > 12) {
				pMesReferencia = 1;
				pAnoReferencia += 1;
			}
			Date hoje = Util.dataAtual();
			// verifica se exite o lançamento saldoMesAnterior já criado
			List<Lancamento> listaLancamentos = this.lancamentoRespository.findByLancamentoSaldoMesAnterior(idUser,
					pMesReferencia, pAnoReferencia);
			if (listaLancamentos.isEmpty()) { // nenhum lancamento criado
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
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	public Optional<Lancamento> findById(UUID id) {
		return lancamentoRespository.findById(id);
	}

	@Transactional
	public void delete(UUID id) {
		try {
			if (existsLancamento(id)) {
				lancamentoRespository.deleteById(id);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	public Object saveOrUpdate(Lancamento pLancamento) {
		try {
			if (pLancamento.getId() != null) {
				return update(new LancamentoDTO(pLancamento));
			}
			return save(pLancamento.getUser().getId(), pLancamento);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	public Lancamento save(UUID idUser, Lancamento pLancamento) {
		try {
			pLancamento.setUser(usuarioService.findUserById(idUser));

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
				pLancamento
						.setDescricao(pLancamento.getDescricao() + " " + 1 + "/" + pLancamento.getQuantidadeParcelas());
			}

			return lancamentoRespository.save(pLancamento);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}

	}

	@Transactional
	public Optional<Lancamento> update(LancamentoDTO pLancamentoDTO) {
		try {
			lancamentoRespository.save(new Lancamento(pLancamentoDTO));
			return findById(pLancamentoDTO.getId());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	private void saveLancamentosProxMensal(int pQuantidadedeMensal, Lancamento pLancamento) {
		try {
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
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
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

	// retorna quantidade de parcelas do ano atual já salvas
	@Transactional
	private int saveLancamentoParcelasAnoAtual(Lancamento pLancamento) {
		try {
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
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	@SuppressWarnings("deprecation")
	private Lancamento manipulaDadosLancamento(Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		try {
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
									pLancamento.getDataVencimento().getDay(),
									pLancamento.getDataVencimento().getHours(),
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
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	// salva lancamentos parcela
	@Transactional
	private void saveLancamentoParcelas(int i, Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		try {
			Lancamento lancamento = manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia);
			lancamento.setPago(false);
			lancamento.setDescricao(i + "/" + pLancamento.getQuantidadeParcelas() + " " + pLancamento.getDescricao());
			lancamento.setNumeroParcela(i);
			lancamentoRespository.save(lancamento);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	private BigDecimal buscaTotalLancamentoPorMesAno(UUID idUser, int pMesReferencia, int pAnoReferencia) {
		BigDecimal saldo = new BigDecimal(0);
		try {
			saldo = new BigDecimal(
					this.lancamentoRespository.buscaTotalLancamentoPorMes(idUser, pMesReferencia, pAnoReferencia));
		} catch (Exception e) {
			// System.out.println("Saldo do mes anterior vazio.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
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

	@Transactional
	public void updatePago(UUID idLancamento, boolean isPago) {
		lancamentoRespository.updatePago(idLancamento, isPago);
	}
}