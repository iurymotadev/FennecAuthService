package com.fennec.authservice.service;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fennec.authservice.controller.request.RegisterRequest;
import com.fennec.authservice.exception.BusinessException;
import com.fennec.authservice.model.Role;
import com.fennec.authservice.model.User;
import com.fennec.authservice.repository.RoleRepository;
import com.fennec.authservice.repository.UserRepository;
import com.fennec.authservice.validation.ConstraintsViolationsInfoGetter;
import com.fennec.authservice.validation.RequestValidator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegisterService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public HttpStatus register(RegisterRequest registerRequest) {
		Role basicRole = roleRepository.findByName(Role.Values.BASIC.toString());

		Boolean usernameInUse = userRepository.findByUsername(registerRequest.getUsername()).stream()
				.anyMatch(userExists -> userExists.getUsername().equals(registerRequest.getUsername()));

		if (usernameInUse) {
			throw new BusinessException("Username already in use, please change and try again");
		} else if (!RequestValidator.registerRequestIsValid(registerRequest)) {
			throw new BusinessException(
					ConstraintsViolationsInfoGetter.registerRequestViolationsMessages(registerRequest));
		}

		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRoles(Set.of(basicRole));

		userRepository.save(user);

		return HttpStatus.CREATED;
	}

}
