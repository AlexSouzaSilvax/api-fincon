package com.fincon.controller;

import com.fincon.dto.UserDTO;
import com.fincon.dto.UserUpdateDTO;
import com.fincon.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "User", description = "User API")
public class UsuarioController {

	private UsuarioService usuarioService;

	@GetMapping("find-all")
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@PostMapping("update")
	public ResponseEntity<Object> update(@RequestBody UserUpdateDTO pUserUpdateDTO) {
		usuarioService.update(pUserUpdateDTO);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody UUID id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("esqueci-senha")
	public ResponseEntity<Object> esqueciSenha(@RequestBody String email) {
		usuarioService.esqueciSenha(email);
		return ResponseEntity.noContent().build();
	}
}