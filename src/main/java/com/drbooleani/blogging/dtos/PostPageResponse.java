package com.drbooleani.blogging.dtos;

import java.time.LocalDateTime;

public record PostPageResponse(
		Integer id,
		String title,
		String thumbnail,
		String category,
		String[] tags,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
