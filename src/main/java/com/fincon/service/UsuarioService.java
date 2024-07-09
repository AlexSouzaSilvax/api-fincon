package com.fincon.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fincon.dto.UserDTO;
import com.fincon.model.User;
import com.fincon.repository.UserRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private UserRepository userRespository;

	private UserDTO userDTO;

	@Transactional
	public List<UserDTO> findAll() {
		return userDTO.UserToUserDTO(userRespository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	public Object findById(UUID id) {
		return userRespository.findById(id);
	}

	public User save(User pUser) {
		// pUser.setDataCriacao(Util.dataAtual());
		return userRespository.save(pUser);
	}

	public User update(User pUser) {
		return userRespository.save(pUser);
	}

	public void delete(UUID id) {
		userRespository.deleteById(id);
	}
}