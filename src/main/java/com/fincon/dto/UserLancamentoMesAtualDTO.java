package com.fincon.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLancamentoMesAtualDTO {

    private UUID id;
    private String email;
    private String nome;

}
