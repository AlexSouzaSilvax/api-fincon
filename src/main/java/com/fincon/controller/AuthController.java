package com.fincon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fincon.service.AuthorizationService;
import com.fincon.dto.AuthenticationDTO;
import com.fincon.dto.RegisterDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
   
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authetinticationDTO){
        return authorizationService.login(authetinticationDTO);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody RegisterDTO registerDTO){
        return authorizationService.register(registerDTO);
    }
}
