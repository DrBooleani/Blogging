package com.drbooleani.blogging.dtos;

public record PostRequest(
	String title,
	String thumbnail,
	String content,
	String category,
	String[] tags
) {

}
