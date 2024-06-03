package com.fennec.authservice.validation;

import java.util.Set;
import java.util.stream.Collectors;

import com.fennec.authservice.controller.request.LoginRequest;
import com.fennec.authservice.controller.request.RegisterRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ConstraintsViolationsInfoGetter {

	public static Set<ConstraintViolation<RegisterRequest>> registerRequestViolations(RegisterRequest registerRequest) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

		return violations;
	}

	public static String registerRequestViolationsMessages(RegisterRequest registerRequest) {

		Set<ConstraintViolation<RegisterRequest>> violations = registerRequestViolations(registerRequest);
		Set<String> messages = violations.stream().map(violation -> violation.getMessage()).collect(Collectors.toSet());

		if (messages.size() > 1) {
			return messages.stream().collect(Collectors.joining("; "));
		}
		return messages.stream().toList().get(0);
	}

	public static Set<ConstraintViolation<LoginRequest>> loginRequestViolations(LoginRequest loginRequest) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

		return violations;
	}

	public static String loginRequestViolationsMessages(LoginRequest loginRequest) {

		Set<ConstraintViolation<LoginRequest>> violations = loginRequestViolations(loginRequest);
		Set<String> messages = violations.stream().map(violation -> violation.getMessage()).collect(Collectors.toSet());

		if (messages.size() > 1) {
			return messages.stream().collect(Collectors.joining("; "));
		}
		return messages.stream().toList().get(0);
	}

}
