package com.fincon.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fincon.Util.GeradorSenha;
import com.fincon.dto.UserDTO;
import com.fincon.model.User;
import com.fincon.repository.LancamentoRepository;
import com.fincon.repository.UserRepository;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private UserRepository userRepository;

	private LancamentoRepository lancamentoRespository;

	private UserDTO userDTO;

	@Transactional
	public List<UserDTO> findAll() {
		return userDTO.UserToUserDTO(userRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	public Object findById(UUID idUser) {
		return userRepository.findById(idUser);
	}

	public User save(User pUser) {
		return userRepository.save(pUser);
	}

	public User update(User pUser) {
		return userRepository.save(pUser);
	}

	public void delete(UUID idUser) {
		if (existsUser(idUser)) {
			if (existsLancamentoUser(idUser)) {
				lancamentoRespository.deleteAllLancamentosPorUser(idUser);
			}
			userRepository.deleteById(idUser);
		}
	}

	public boolean existsUser(UUID idUser) {
		return userRepository.existsById(idUser);
	}

	public boolean existsLancamentoUser(UUID idUser) {
		return lancamentoRespository.existsLancamentoUser(idUser);
	}

	public void esqueciSenha(String pEmail) {
		String novaSenha = new GeradorSenha().geraSenhaAleatoria();		
		this.updateSenhaByEmail(pEmail, new BCryptPasswordEncoder().encode(novaSenha));
		new EmailEsqueciSenhaService().enviar(pEmail, this.findUsernameByEmail(pEmail), novaSenha);
	}

	private String findUsernameByEmail(String pEmail) {
		return userRepository.findUsernameByEmail(pEmail);
	}

	private String updateSenhaByEmail(String pEmail, String pSenha) {
		userRepository.updateSenhaByEmail(pEmail, pSenha);
		return "";
	}
}