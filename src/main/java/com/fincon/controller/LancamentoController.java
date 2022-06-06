package com.fincon.controller;

import com.fincon.model.Lancamento;
import com.fincon.model.ReturnError;
import com.fincon.service.LancamentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
public class LancamentoController {
    private LancamentoService lancamentoService;

    @PostMapping
    @RequestMapping("create")
    public ResponseEntity<Object> create(@RequestBody Lancamento pLancamento) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoService.save(pLancamento));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
                            "/api/lancamento/create"));
        }

    }

}
