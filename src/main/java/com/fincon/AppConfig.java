package com.fincon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fincon.dto.LancamentoDTO;
import com.fincon.dto.UserDTO;

@Configuration
public class AppConfig {

    @Bean
    public UserDTO userDTO() {
        return new UserDTO();
    }

    @Bean
    public LancamentoDTO lancamentoDTO() {
        return new LancamentoDTO();
    }
}