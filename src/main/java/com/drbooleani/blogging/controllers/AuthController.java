package com.drbooleani.blogging.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drbooleani.blogging.dtos.LoginRequest;
import com.drbooleani.blogging.dtos.LoginResponse;
import com.drbooleani.blogging.models.User;
import com.drbooleani.blogging.services.AuthService;
import com.drbooleani.blogging.services.JwtService;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
	
	private final JwtService jwtService;
	private final AuthService authService;
	
	public AuthController(JwtService jwtService, AuthService authService) {
		this.jwtService = jwtService;
		this.authService = authService;
	}
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
