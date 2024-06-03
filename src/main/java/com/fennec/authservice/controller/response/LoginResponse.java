package com.fennec.authservice.controller.response;

public record LoginResponse(String accessToken, Long expiresIn) {
}
