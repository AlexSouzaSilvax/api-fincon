package com.fincon.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nome", length = 9999)
	private String nome;

	@Column(name = "email", length = 9999)
	private String email;

	@Column(name = "login", length = 9999)
	private String login;

	@Column(name = "senha", length = 9999)
	private String senha;

	@Column(name = "data_criacao", nullable = false)
	@JsonProperty("data_criacao")
	private LocalDateTime dataCriacao;

}
