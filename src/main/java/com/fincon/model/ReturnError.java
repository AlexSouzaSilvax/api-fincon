package com.fincon.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ReturnError {

    private Date timestamp = new Date();
    private int status;
    private String error;
    private String message;
    private String path;

    public ReturnError(HttpStatus pStatus, String error, String message, String path) {
        this.status = pStatus.value();
        this.error = error;
        this.message = message;
        this.path = path;
    }
}