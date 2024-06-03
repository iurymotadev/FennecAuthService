package com.fennec.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fennec.authservice.controller.request.LoginRequest;
import com.fennec.authservice.controller.response.LoginResponse;
import com.fennec.authservice.service.LoginService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

		LoginResponse loginResponse = loginService.login(loginRequest);
		return ResponseEntity.ok(loginResponse);

	}

}
