package com.fincon.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincon.enums.UserRole;
import com.fincon.listeners.UsuarioEventListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@EntityListeners(UsuarioEventListener.class)
@Table(name = "Usuario")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "nome", length = 9999)
	private String nome;

	@Column(name = "email", length = 9999)
	private String email;

	@Column(name = "celular", length = 9999)
	private String celular;

	@Column(name = "username", length = 9999)
	private String username;

	@Column(name = "password", length = 9999)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, updatable = false)
	@JsonProperty("data_criacao")
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_atualizacao", nullable = true)
	private Date dataAtualizacao;

	public User() {
	}

	public User(UUID pId) {
		this.id = pId;
	}

	public User(String nome, String email, String username, String password, UserRole userRole) {
		this.nome = nome;
		this.email = email;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}

	public User(UUID id, String email, String nome) {
		this.id = id;
		this.email = email;
		this.nome = nome;
	}

	@SuppressWarnings("static-access")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.userRole == userRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;

	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
