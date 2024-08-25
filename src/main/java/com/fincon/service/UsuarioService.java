package com.fincon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fincon.Util.EmailValidator;
import com.fincon.Util.GeradorSenha;
import com.fincon.Util.Util;
import com.fincon.dto.UserDTO;
import com.fincon.dto.UserLancamentoMesAtualDTO;
import com.fincon.dto.UserUpdateDTO;
import com.fincon.model.User;
import com.fincon.repository.LancamentoRepository;
import com.fincon.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	public User findUserById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado pelo ID: " + id));
	}

	public User save(User pUser) {
		return userRepository.save(pUser);
	}

	public void update(UserUpdateDTO pUserUpdateDTO) {
		pUserUpdateDTO.setPassword(new BCryptPasswordEncoder().encode(pUserUpdateDTO.getPassword()));
		userRepository.userUpdateDTO(pUserUpdateDTO.getNome(), pUserUpdateDTO.getEmail(),
				pUserUpdateDTO.getCelular(), pUserUpdateDTO.getUsername(), pUserUpdateDTO.getPassword(),
				pUserUpdateDTO.getId(), Util.dataAtual());
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
		pEmail = pEmail.trim().replaceAll("^\"|\"$", "").toLowerCase();
		if (EmailValidator.isValidEmail(pEmail)) {
			if (this.existsUserByEmail(pEmail)) {
				String novaSenha = new GeradorSenha().geraSenhaAleatoria();
				this.updateSenhaByEmail(new BCryptPasswordEncoder().encode(novaSenha), pEmail);
				new EmailEsqueciSenhaService().enviar(pEmail,
						this.findUsernameByEmail(pEmail), novaSenha);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "E-mail não encontrado");
			}
		} else {
			throw new IllegalArgumentException("E-mail informado inválido");
		}
	}

	private String findUsernameByEmail(String pEmail) {
		return userRepository.findUsernameByEmail(pEmail);
	}

	private void updateSenhaByEmail(String pSenha, String pEmail) {
		userRepository.updateSenhaByEmail(pSenha, pEmail);
	}

	public List<UserLancamentoMesAtualDTO> findUserLancamentoMesAtual(int pMesReferencia, int pAnoReferencia) {

		List<UserLancamentoMesAtualDTO> listaUserLancamentoMesAtualDTO = new ArrayList<>();

		for (User user : userRepository.findUserLancamentoMesAtual(pMesReferencia, pAnoReferencia)) {
			listaUserLancamentoMesAtualDTO
					.add(new UserLancamentoMesAtualDTO(user.getId(), user.getEmail(), user.getNome()));
		}

		return listaUserLancamentoMesAtualDTO;
	}

	public String findIdByUsername(String pUsername) {
		return userRepository.findIdByUsername(pUsername);
	}

	public boolean existsUserByEmail(String pEmail) {
		return userRepository.existsUserByEmail(pEmail);
	}

}