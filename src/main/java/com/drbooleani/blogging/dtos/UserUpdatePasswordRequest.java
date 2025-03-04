package com.drbooleani.blogging.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdatePasswordRequest(
		@NotNull 
		@NotBlank
		String oldPassword,
		@NotNull 
		@NotBlank
		String newPassword,
		@NotNull 
		@NotBlank
		String confirmPassword
) {

}
