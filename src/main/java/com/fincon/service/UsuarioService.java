package com.fincon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fincon.Util.Util;
import com.fincon.dto.UsuarioAccessDTO;
import com.fincon.model.Usuario;
import com.fincon.repository.UsuarioRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private UsuarioRepository usuarioRespository;

	public List<Usuario> findAll() {
		return usuarioRespository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	public Object findById(Long id) {
		return usuarioRespository.findById(id);
	}

	public Usuario access(UsuarioAccessDTO pUsuario) {
		return usuarioRespository.access(pUsuario.getLogin(), pUsuario.getSenha());
	}

	public Usuario save(Usuario pUsuario) {
		pUsuario.setDataCriacao(Util.dataAtual());
		return usuarioRespository.save(pUsuario);
	}

	public Usuario update(Usuario pUsuario) {
		return usuarioRespository.save(pUsuario);
	}

	public void delete(Long id) {
		usuarioRespository.deleteById(id);
	}
}