package com.fennec.authservice.validation;

import java.util.Set;

import com.fennec.authservice.controller.request.LoginRequest;
import com.fennec.authservice.controller.request.RegisterRequest;

import jakarta.validation.ConstraintViolation;

public class RequestValidator {

	public static Boolean registerRequestIsValid(RegisterRequest registerRequest) {
		Set<ConstraintViolation<RegisterRequest>> violations = ConstraintsViolationsInfoGetter
				.registerRequestViolations(registerRequest);

		if (!violations.isEmpty()) {
			return false;
		}
		return true;
	}

	public static Boolean loginRequestIsValid(LoginRequest loginRequest) {
		Set<ConstraintViolation<LoginRequest>> violations = ConstraintsViolationsInfoGetter
				.loginRequestViolations(loginRequest);

		if (!violations.isEmpty()) {
			return false;
		}
		return true;
	}

}
