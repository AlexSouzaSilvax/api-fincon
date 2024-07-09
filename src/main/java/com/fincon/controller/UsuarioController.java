package com.fincon.controller;

import com.fincon.model.User;
import com.fincon.model.ReturnError;
import com.fincon.service.UsuarioService;

import java.util.UUID;

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
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin
public class UsuarioController {

	private UsuarioService usuarioService;

	/*
	@PostMapping
	@RequestMapping("access")
	public ResponseEntity<Object> access(@RequestBody UsuarioAccessDTO pUsuario) {
		Usuario usuario = new Usuario();
		try {
			usuario = usuarioService.access(pUsuario);
			if (usuario != null) {
				return ResponseEntity.status(HttpStatus.OK).header("access", "true").body(usuario);
			} else {				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).header("access", "false").body(new ReturnError(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), "Usuário não encontrado", "/api/usuario/create"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("access", "false")
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/create"));
		}

	}
	*/

	@GetMapping
	@RequestMapping("find-all")
	public ResponseEntity<Object> findAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/find-all"));
		}
	}

	@GetMapping
	@RequestMapping("find-by-id")
	public ResponseEntity<Object> findById(@RequestParam("id") UUID pId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(pId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/findById"));
		}
	}

	@PostMapping
	@RequestMapping("create")
	public ResponseEntity<Object> create(@RequestBody User pUsuario) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(pUsuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/create"));
		}

	}

	@PostMapping
	@RequestMapping("update")
	public ResponseEntity<Object> update(@RequestBody User pUsuario) {
		try {
			usuarioService.update(pUsuario);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/update"));
		}

	}

	@PostMapping
	@RequestMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody UUID id) {
		try {
			usuarioService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
							"/api/usuario/delete"));
		}

	}

}
