package com.drbooleani.blogging.dtos;

public record UserResponse(
		Integer id,
		String fullName,
		String email,
		String profileUrl,
		String role
) {

}
