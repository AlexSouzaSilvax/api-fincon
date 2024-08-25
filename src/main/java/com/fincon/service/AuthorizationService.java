package com.fincon.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fincon.Util.EmailValidator;
import com.fincon.dto.AuthenticationDTO;
import com.fincon.dto.LoginResponseDTO;
import com.fincon.dto.RegisterDTO;
import com.fincon.exceptions.UserAlreadyExistsException;
import com.fincon.model.User;
import com.fincon.repository.UserRepository;
import com.fincon.security.TokenService;

import jakarta.validation.Valid;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        String username = data.username().trim().toLowerCase();
        if (username.isEmpty() && username.isBlank() && data.password().isEmpty()) {
            throw new IllegalArgumentException("Usuário/Senha informado inválido");
        }

        authenticationManager = context.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(username, data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        String idUsuario = usuarioService.findIdByUsername(username);
        return ResponseEntity.ok(new LoginResponseDTO(token, idUsuario, username));
    }

    @Transactional
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        String username = registerDTO.username().trim().toLowerCase();

        if (!EmailValidator.isValidEmail(registerDTO.email().trim().replaceAll("^\"|\"$", "").toLowerCase())) {
            throw new IllegalArgumentException("E-mail informado inválido");
        }

        if (userRepository.existsUserByEmail(registerDTO.email())) {
            throw new UserAlreadyExistsException("O e-mail fornecido já está cadastrado");
        }

        if (userRepository.existsUserByUsername(username)) {
            throw new UserAlreadyExistsException("O nome de usuário fornecido já está em uso");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.nome(), registerDTO.email(), username, encryptedPassword,
                registerDTO.role());
        newUser.setDataCriacao(new Date(System.currentTimeMillis()));

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}