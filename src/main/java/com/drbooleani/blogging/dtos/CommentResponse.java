package com.drbooleani.blogging.dtos;

import java.time.LocalDateTime;

public record CommentResponse(
		Integer id,
		String content,
		String userId,
		String userFullName,
		String userProfileUrl,
		Integer postId,
		String postTitle,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
