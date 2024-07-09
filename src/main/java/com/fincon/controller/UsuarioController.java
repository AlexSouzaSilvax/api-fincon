package com.fincon.controller;

import com.fincon.dto.UserDTO;
import com.fincon.service.UsuarioService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin
public class UsuarioController {

	private UsuarioService usuarioService;

	@GetMapping
	@RequestMapping("find-all")
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	/*
	 * @GetMapping
	 * 
	 * @RequestMapping("find-by-id")
	 * public ResponseEntity<Object> findById(@RequestParam("id") UUID pId) {
	 * try {
	 * return
	 * ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(pId));
	 * } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
	 * HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
	 * "/api/usuario/findById"));
	 * }
	 * }
	 * 
	 * @PostMapping
	 * 
	 * @RequestMapping("create")
	 * public ResponseEntity<Object> create(@RequestBody User pUsuario) {
	 * try {
	 * return
	 * ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(pUsuario))
	 * ;
	 * } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
	 * HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
	 * "/api/usuario/create"));
	 * }
	 * 
	 * }
	 * 
	 * @PostMapping
	 * 
	 * @RequestMapping("update")
	 * public ResponseEntity<Object> update(@RequestBody User pUsuario) {
	 * try {
	 * usuarioService.update(pUsuario);
	 * return ResponseEntity.status(HttpStatus.CREATED)
	 * .body(true);
	 * } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
	 * HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
	 * "/api/usuario/update"));
	 * }
	 * 
	 * }
	 * 
	 * @PostMapping
	 * 
	 * @RequestMapping("delete")
	 * public ResponseEntity<Object> delete(@RequestBody UUID id) {
	 * try {
	 * usuarioService.delete(id);
	 * return ResponseEntity.status(HttpStatus.OK).body(null);
	 * } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(new ReturnError(HttpStatus.INTERNAL_SERVER_ERROR,
	 * HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
	 * "/api/usuario/delete"));
	 * }
	 * 
	 * }
	 */
}
