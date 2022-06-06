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
			pLancamento.setDataLancamento(LocalDateTime.parse(LocalDateTime.now().minusDays(1).minusMonths(1).minusHours(2).toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		}

		if (pLancamento.isPago()) {
			pLancamento.setDataPagamento(LocalDateTime.now().minusDays(1).minusMonths(1).minusHours(2));
		}

		// quando for mensal
		if (pLancamento.isMensal() && pLancamento.getTipoPagamento() != 3) {
			System.out.println("é mensal");
		}

		// quando for credito
		if (pLancamento.getQuantidadeParcelas() > 1) {
			System.out.println("é parcelado, " + pLancamento.getQuantidadeParcelas() + " parcelas.");
			// criando proximas parcelas
			for (int i = 2; i < (pLancamento.getQuantidadeParcelas() + 1); i++) {
				Lancamento lancamento = new Lancamento();
				lancamento.setTipoLancamento(pLancamento.getTipoLancamento());
				lancamento.setValor(pLancamento.getValor());
				lancamento.setMensal(pLancamento.isMensal());
				lancamento.setPago(pLancamento.isPago());
				lancamento.setTipoPagamento(pLancamento.getTipoPagamento());
				lancamento.setQuantidadeParcelas(pLancamento.getQuantidadeParcelas());
				
				//popular com o mes seguinte
				lancamento.setMesReferencia(pLancamento.getMesReferencia());
				
				LocalDateTime.of(2018, 07, 22, 10, 15, 30);
				
				
				
				
				
				
				
				
				//popular com o ano seguinte
				lancamento.setAnoReferencia(pLancamento.getAnoReferencia());				
				lancamento.setDataLancamento(pLancamento.getDataLancamento());
				//popular com o mes seguinte
				lancamento.setDataVencimento(pLancamento.getDataVencimento());				
				lancamento
						.setDescricao(pLancamento.getDescricao() + " " + i + "/" +
								pLancamento.getQuantidadeParcelas());
				lancamento.setNumeroParcela(i);
				lancamento.setObservacao(pLancamento.getObservacao());
				lancamentoRespository.save(lancamento);
			}
			pLancamento.setNumeroParcela(1);
			pLancamento
					.setDescricao(pLancamento.getDescricao() + " " + 1 + "/" +
							pLancamento.getQuantidadeParcelas());
		}

		return lancamentoRespository.save(pLancamento);
	}

	public void delete(Long id) {
		lancamentoRespository.deleteById(id);
	}

}