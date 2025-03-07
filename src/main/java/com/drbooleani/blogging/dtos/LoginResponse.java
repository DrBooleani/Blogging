package com.drbooleani.blogging.dtos;

public record LoginResponse(
		String token,
		long expiresIn
) {

}
