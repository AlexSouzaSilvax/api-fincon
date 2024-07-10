package com.fincon.controller;

import com.fincon.dto.UserDTO;
import com.fincon.model.User;
import com.fincon.service.UsuarioService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin
public class UsuarioController {

	private UsuarioService usuarioService;

	@GetMapping("find-all")
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@PostMapping("update")
	public ResponseEntity<Object> update(@RequestBody User pUsuario) {
		return ResponseEntity.ok(usuarioService.update(pUsuario));
	}

	@PostMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody UUID id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
}