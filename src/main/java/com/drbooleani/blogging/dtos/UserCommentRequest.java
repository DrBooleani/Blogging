package com.drbooleani.blogging.dtos;

public record UserCommentRequest(
		String content,
		Integer postId,
		Integer userId
) {

}
