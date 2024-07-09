package com.fincon.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fincon.model.User;
import com.fincon.repository.UserRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private UserRepository UserRespository;

	public List<User> findAll() {
		return UserRespository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	public Object findById(UUID id) {
		return UserRespository.findById(id);
	}

	public User save(User pUser) {
		//pUser.setDataCriacao(Util.dataAtual());
		return UserRespository.save(pUser);
	}

	public User update(User pUser) {
		return UserRespository.save(pUser);
	}

	public void delete(UUID id) {
		UserRespository.deleteById(id);
	}
}