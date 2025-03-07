package com.drbooleani.blogging.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drbooleani.blogging.dtos.LoginRequest;
import com.drbooleani.blogging.models.User;
import com.drbooleani.blogging.repositories.UserRepository;
import com.drbooleani.blogging.services.exceptions.BadRequestException;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}


	public User authenticate(LoginRequest input) throws BadRequestException {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        var user = this.userRepository.findByEmail(input.email())
                .orElseThrow(() -> new BadRequestException("Invalid E-mail/Password"));
        
        if (passwordEncoder.matches(input.password(), user.getPasswordHash())) {
        	return user;
        }
        
        throw new BadRequestException("Invalid E-mail/Password");
       
    }
}
