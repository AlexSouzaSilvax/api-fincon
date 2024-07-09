package com.fincon.controller;

import com.fincon.dto.LancamentoDTO;
import com.fincon.model.Lancamento;
import com.fincon.service.LancamentoService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
@CrossOrigin
public class LancamentoController {
	private LancamentoService lancamentoService;

	@GetMapping
	@RequestMapping("find-all")
	public ResponseEntity<List<LancamentoDTO>> findAll() {
		return ResponseEntity.ok(lancamentoService.findAll());
	}

	@GetMapping
	@RequestMapping("find-list-main")
	public ResponseEntity<Object> findListMain(@RequestParam("id_usuario") UUID idUsuario,
			@RequestParam("mes_referencia") int pMesReferencia,
			@RequestParam("ano_referencia") int pAnoReferencia) {
		return ResponseEntity.ok(lancamentoService.findListMain(idUsuario, pMesReferencia, pAnoReferencia));
	}

	@PostMapping
	@RequestMapping("create")
	public ResponseEntity<Object> create(@RequestBody Lancamento pLancamento) {
		lancamentoService.saveOrUpdate(pLancamento);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	@RequestMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody UUID id) {
		lancamentoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
