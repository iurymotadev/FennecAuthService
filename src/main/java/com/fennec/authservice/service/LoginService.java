package com.fennec.authservice.service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.fennec.authservice.controller.request.LoginRequest;
import com.fennec.authservice.controller.response.LoginResponse;
import com.fennec.authservice.exception.BusinessException;
import com.fennec.authservice.model.Role;
import com.fennec.authservice.model.User;
import com.fennec.authservice.repository.UserRepository;
import com.fennec.authservice.validation.ConstraintsViolationsInfoGetter;
import com.fennec.authservice.validation.RequestValidator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoginService {

	private final JwtEncoder jwtEncoder;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {
		Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

		if (user.isEmpty() || !(user.get().loginIsCorrect(loginRequest, this.passwordEncoder))) {
			throw new BusinessException("Invalid username or password");
		} else if (!RequestValidator.loginRequestIsValid(loginRequest)) {
			throw new BusinessException(ConstraintsViolationsInfoGetter.loginRequestViolationsMessages(loginRequest));
		}

		Instant now = Instant.now();
		Long expiresIn = 300L;
		//@formatter:off
	    String scopes =
	        user.get()
	            .getRoles()
	            .stream()
	            .map(Role::getName)
	            .collect(Collectors.joining(" "));
	
	    JwtClaimsSet claims = JwtClaimsSet.builder()
	        .issuer("mybackend")
	        .subject(user.get().getUserId().toString())
	        .issuedAt(now)
	        .expiresAt(now.plusSeconds(expiresIn))
	        .claim("scope", scopes)
	        .build();
	    //@formatter:on

		String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		return new LoginResponse(jwtValue, expiresIn);
	}

}
