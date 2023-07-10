package com.fincon.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fincon.Util.Util;
import com.fincon.enums.Categoria;
import com.fincon.enums.TipoLancamento;
import com.fincon.enums.TipoPagamento;
import com.fincon.model.LancamentoFixo;
import com.fincon.model.Usuario;
import com.fincon.repository.LancamentoFixoRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LancamentoFixoService {

	private LancamentoFixoRepository LancamentoFixoRespository;

	public List<LancamentoFixo> findByUsuario(Long id) {
		return LancamentoFixoRespository.findByUsuario(id);
	}

	public void delete(Long id) {
		LancamentoFixoRespository.deleteById(id);
	}

	public LancamentoFixo save(Long idUsuario, LancamentoFixo pLancamentoFixo) {
		pLancamentoFixo.setUsuario(new Usuario(idUsuario));

		if (pLancamentoFixo.getId() == null) {
			pLancamentoFixo.setDataLancamentoFixo(Util.dataAtual());
		}
		return LancamentoFixoRespository.save(pLancamentoFixo);
	}

	public void update() {
		/*
		 * lancamentoRespository.updateAllById(pLancamentoFixo.getAnoReferencia(),
		 * Integer.valueOf(pLancamentoFixo.getCategoria()),
		 * pLancamentoFixo.getDataPrevistaPagamento(),
		 * pLancamentoFixo.getDataVencimento(),
		 * pLancamentoFixo.getDescricao(),
		 * pLancamentoFixo.isMensal(),
		 * pLancamentoFixo.getMesReferencia(),
		 * pLancamentoFixo.getObservacao(),
		 * pLancamentoFixo.isPago(),
		 * pLancamentoFixo.getQuantidadeParcelas(),
		 * Integer.valueOf(pLancamentoFixo.getTipoLancamentoFixo()),
		 * Integer.valueOf(pLancamentoFixo.getTipoPagamento()),
		 * pLancamentoFixo.getValor(),
		 * pLancamentoFixo.getId());
		 * }
		 */
	}
}