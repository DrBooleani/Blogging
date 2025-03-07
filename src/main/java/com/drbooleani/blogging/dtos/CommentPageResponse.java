package com.drbooleani.blogging.dtos;

import java.time.LocalDateTime;

public record CommentPageResponse(
		Integer id,
		String content,
		Integer userId,
		String fullName,
		String profilePicture,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
