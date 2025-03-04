package com.drbooleani.blogging.dtos;

import java.time.LocalDateTime;

public record PostResponse(
		Integer id,
		String title,
		String thumbnail,
		String content,
		String category,
		String[] tags,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
