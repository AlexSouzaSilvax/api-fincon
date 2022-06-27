package com.fincon.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fincon.Util.Util;
import com.fincon.dto.LancamentoDTO;
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
		List<LancamentoDTO> listaLancamentoDTO = new ArrayList<>();
		for (Lancamento pLancamento : lancamentoRespository.findListMain(idUsuario, pMesReferencia, pAnoReferencia)) {
			listaLancamentoDTO.add(new LancamentoDTO(pLancamento));
		}
		return listaLancamentoDTO;
	}

	public Object findById(Long id) {
		return lancamentoRespository.findById(id);
	}

	public void delete(Long id) {
		lancamentoRespository.deleteById(id);
	}

	public Lancamento save(Long idUsuario, Lancamento pLancamento) {		
		pLancamento.setUsuario((Usuario) findById(idUsuario));

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
				Integer.valueOf(pLancamento.getTipoLancamento()),
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

	// salva lancamentos parcela
	private void saveLancamentoParcelas(int i, Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = manipulaDadosLancamento(pLancamento, novoMesReferencia, novoAnoReferencia);
		lancamento.setDescricao(pLancamento.getDescricao() + " " + i + "/" + pLancamento.getQuantidadeParcelas());
		lancamento.setNumeroParcela(i);
		lancamentoRespository.save(lancamento);
	}

	private Lancamento manipulaDadosLancamento(Lancamento pLancamento, int novoMesReferencia, int novoAnoReferencia) {
		Lancamento lancamento = new Lancamento();
		// trata mes fevereiro
		if (novoMesReferencia == 2) {
			if (pLancamento.getDataVencimento().getDayOfMonth() >= 29
					|| pLancamento.getDataPrevistaPagamento().getDayOfMonth() >= 29) {
				if (lancamento.getDataVencimento() != null) {
					lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
							pLancamento.getDataPrevistaPagamento().getHour(),
							pLancamento.getDataPrevistaPagamento().getMinute()));
				}
				if (lancamento.getDataPrevistaPagamento() != null) {
					lancamento.setDataPrevistaPagamento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia, 28,
							pLancamento.getDataPrevistaPagamento().getHour(),
							pLancamento.getDataPrevistaPagamento().getMinute()));
				}
			}
		} else {
			if (lancamento.getDataVencimento() != null) {
				lancamento.setDataVencimento(LocalDateTime.of(novoAnoReferencia, novoMesReferencia,
						pLancamento.getDataVencimento().getDayOfMonth(), pLancamento.getDataVencimento().getHour(),
						pLancamento.getDataVencimento().getMinute()));
			}
			if (lancamento.getDataPrevistaPagamento() != null) {
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

}