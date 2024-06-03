package com.fennec.authservice.model;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fennec.authservice.controller.request.LoginRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;

	@Column(name = "user_name")
	private String username;

	@Column(name = "user_password")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "user_roles")
	//@formatter:off
   @JoinTable(
      name = "users_roles", 
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  //@formatter:on
	private Set<Role> roles;

	public boolean loginIsCorrect(LoginRequest loginRequest, BCryptPasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(loginRequest.getPassword(), this.password);
		// retorna map boolean isvalid string message
	}

}
