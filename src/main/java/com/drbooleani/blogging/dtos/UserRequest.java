package com.drbooleani.blogging.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
		@NotNull 
		@NotBlank
		String fullName,
		@NotNull 
		@NotBlank
		@Email
		String email,
		@NotNull 
		@NotBlank
		String password
) {

}
