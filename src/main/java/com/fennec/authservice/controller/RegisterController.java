package com.fennec.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fennec.authservice.controller.request.RegisterRequest;
import com.fennec.authservice.service.RegisterService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
		HttpStatus registerResponse = registerService.register(registerRequest);

		return ResponseEntity.status(registerResponse).build();
	}
}
