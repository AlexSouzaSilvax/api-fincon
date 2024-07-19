package com.fincon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Email {
    private String destinatario;
    private String titulo;
    private String nomeDestinatario;
    private Object anexo;
    private String conteudo;
}
