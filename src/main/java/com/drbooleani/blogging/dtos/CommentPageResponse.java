package com.drbooleani.blogging.dtos;

import java.time.LocalDateTime;

public record CommentPageResponse(
		Integer id,
		String content,
		Integer userId,
		Integer postId,
		String postTitle,
		String fullName,
		String profilePicture,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
