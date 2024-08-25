package com.fincon.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fincon.dto.LancamentoDTO;
import com.fincon.model.Lancamento;
import com.fincon.service.LancamentoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/lancamentos")
@Tag(name = "Lançamentos", description = "Lançamentos API")
public class LancamentoController {

	@Autowired
	private LancamentoService lancamentoService;

	@PostMapping("update-pago")
	public ResponseEntity<Object> updatePago(@RequestParam("id") UUID idLancamento,
			@RequestParam("pago") boolean isPago) {
		lancamentoService.updatePago(idLancamento, isPago);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("find-list-main")
	public ResponseEntity<Object> findListMain(@RequestParam("id_usuario") UUID idUsuario,
			@RequestParam("mes_referencia") int pMesReferencia,
			@RequestParam("ano_referencia") int pAnoReferencia) {
		return ResponseEntity.ok(lancamentoService.findListMain(idUsuario, pMesReferencia, pAnoReferencia));
	}

	@PostMapping("create")
	public ResponseEntity<Object> create(@RequestBody LancamentoDTO pLancamentoDTO) {
		lancamentoService.saveOrUpdate(new Lancamento(pLancamentoDTO));
		return ResponseEntity.noContent().build();
	}

	@PostMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody String id) {
		lancamentoService.delete(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}

}
