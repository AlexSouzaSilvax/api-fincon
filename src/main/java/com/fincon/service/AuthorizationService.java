package com.fincon.service;

import java.util.Date;
import java.util.UUID;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import com.fincon.Util.EmailValidator;
import com.fincon.dto.AuthenticationDTO;
import com.fincon.dto.LoginResponseDTO;
import com.fincon.dto.RegisterDTO;
import com.fincon.enums.CommonLogEnum;
import com.fincon.exceptions.UserAlreadyExistsException;
import com.fincon.model.CommonLog;
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

    @Autowired
    CommonLogService commonLogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            String username = data.username().trim().toLowerCase();
            if (username.isEmpty() || username.isBlank() && data.password().isEmpty()) {
                throw new IllegalArgumentException("Usuário/Senha informado inválido");
            }

            authenticationManager = context.getBean(AuthenticationManager.class);
            var usernamePassword = new UsernamePasswordAuthenticationToken(username, data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            String idUsuario = usuarioService.findIdByUsername(username);

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, idUsuario, username);

            // Criando Logs Sucesso
            CommonLog commonLog = new CommonLog();
            commonLog.setEtapa(CommonLogEnum.LOGIN);
            commonLog.setDescricao("Usuário: " + data.username() + " realizou login com sucesso!");
            commonLog.setJsonEnvio("Usuário: " + username + " Senha: " + data.password());
            commonLog.setJsonRetorno(loginResponseDTO.toString());
            commonLog.setUsuario(UUID.fromString(idUsuario));
            commonLogService.save(commonLog);

            return ResponseEntity.ok(loginResponseDTO);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        try {
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

            // Criando Logs Sucesso
            CommonLog commonLog = new CommonLog();
            commonLog.setEtapa(CommonLogEnum.NOVO_USUARIO);
            commonLog.setDescricao("Usuário criado com sucesso!");
            commonLog.setJsonEnvio(registerDTO.toString());
            commonLog.setJsonRetorno(newUser.toString());
            commonLog.setUsuario(newUser.getId());
            commonLogService.save(commonLog);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

}