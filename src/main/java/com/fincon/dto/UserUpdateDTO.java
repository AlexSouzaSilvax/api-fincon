package com.fincon.dto;

import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserUpdateDTO {

    UUID id;

    String nome;

    String email;

    String celular;

    String username;

    String password;

    public UserUpdateDTO(UUID id, String nome, String email, String celular, String username, String password) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.username = username;        
        this.password = password;
    }
}