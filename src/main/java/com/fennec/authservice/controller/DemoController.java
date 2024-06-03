package com.fennec.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class DemoController {

	@GetMapping("/demo")
	public String demo() {
		return "Hello";
	}

}
