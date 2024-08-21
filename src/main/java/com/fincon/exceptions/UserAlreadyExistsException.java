package com.fincon.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String mensagem) {
        super(mensagem);
    }

    public UserAlreadyExistsException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
