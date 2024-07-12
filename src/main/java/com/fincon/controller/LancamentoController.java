package com.fincon.controller;

import com.fincon.dto.LancamentoDTO;
import com.fincon.model.Lancamento;
import com.fincon.service.LancamentoService;

import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Lançamentos", description = "Lançamentos API")
public class LancamentoController {
	private LancamentoService lancamentoService;

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
	public ResponseEntity<Object> delete(@RequestBody UUID id) {
		lancamentoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
