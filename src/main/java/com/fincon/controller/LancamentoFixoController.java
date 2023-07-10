package com.fincon.controller;

import com.fincon.dto.LancamentoDTO;
import com.fincon.model.Lancamento;
import com.fincon.model.LancamentoFixo;
import com.fincon.model.ReturnError;
import com.fincon.service.LancamentoFixoService;
import com.fincon.service.LancamentoService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/lancamentos-fixos")
@AllArgsConstructor
@CrossOrigin
public class LancamentoFixoController {
	private LancamentoFixoService lancamentoFixoService;

	@GetMapping
	@RequestMapping("find-by-usuario")
	public ResponseEntity<Object> findByUsuario(@RequestParam("id") Long pId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lancamentoFixoService.findByUsuario(pId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento-fixo/findById"));
		}
	}

	@PostMapping
	@RequestMapping("create")
	public ResponseEntity<Object> create(@RequestParam("id_usuario") long idUsuario,
			@RequestBody LancamentoFixo pLancamentoFixo) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(lancamentoFixoService.save(idUsuario, pLancamentoFixo));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento-fixo/create"));
		}

	}

	@PostMapping
	@RequestMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody Long id) {
		try {
			lancamentoFixoService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento-fixo/delete"));
		}

	}
}
