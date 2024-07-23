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
import org.springframework.web.bind.annotation.RequestBody;

import com.fincon.security.TokenService;
import com.fincon.dto.AuthenticationDTO;
import com.fincon.dto.LoginResponseDTO;
import com.fincon.dto.RegisterDTO;
import com.fincon.model.User;
import com.fincon.repository.UserRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        if (this.userRepository.findByUsername(registerDTO.username()) != null)
            return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.nome(), registerDTO.email(), registerDTO.username(), encryptedPassword,
                registerDTO.role());
        newUser.setDataCriacao(new Date(System.currentTimeMillis()));
        this.userRepository.save(newUser);

        new EmailBemVindoService().enviar(registerDTO.email(), registerDTO.nome());

        return ResponseEntity.ok().build();
    }

}