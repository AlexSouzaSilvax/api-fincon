package com.fincon.controller;

import com.fincon.dto.LancamentoDTO;
import com.fincon.model.Lancamento;
import com.fincon.model.ReturnError;
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
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
@CrossOrigin
public class LancamentoController {
	private LancamentoService lancamentoService;

	@GetMapping
	@RequestMapping("find-all")
	public ResponseEntity<Object> findAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lancamentoService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/findAll"));
		}

	}

	@GetMapping
	@RequestMapping("find-all-order")
	public ResponseEntity<Object> findAllOrderNumeroParcela() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lancamentoService.findAllOrderNumeroParcela());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/findAllOrderNumeroParcela"));
		}

	}

	@GetMapping
	@RequestMapping("find-by-id")
	public ResponseEntity<Object> findById(@RequestParam("id") Long pId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lancamentoService.findById(pId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/findById"));
		}
	}

	@GetMapping
	@RequestMapping("find-list-main")
	public ResponseEntity<Object> findListMain(@RequestParam("id_usuario") Long idUsuario,
			@RequestParam("mes_referencia") int pMesReferencia,
			@RequestParam("ano_referencia") int pAnoReferencia) {
		try {
			lancamentoService.insereSaldoMesSeguinte(idUsuario, pMesReferencia, pAnoReferencia);
			return ResponseEntity.status(HttpStatus.OK)
					.body(lancamentoService.findListMain(idUsuario, pMesReferencia, pAnoReferencia));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/findListMain"));
		}
	}

	@PostMapping
	@RequestMapping("create")
	public ResponseEntity<Object> create(@RequestParam("id_usuario") long idUsuario,
			@RequestBody Lancamento pLancamento) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoService.save(idUsuario, pLancamento));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/create"));
		}

	}

	@PostMapping
	@RequestMapping("update")
	public ResponseEntity<Object> update(@RequestBody LancamentoDTO pLancamentoDTO) {
		try {
			lancamentoService.update(pLancamentoDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/update"));
		}

	}

	@PostMapping
	@RequestMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody Long id) {
		try {
			lancamentoService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/lancamento/delete"));
		}

	}

}
