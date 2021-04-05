package com.acme.blog.domain.comment;

import java.time.LocalDate;

import lombok.Value;

@Value
public class CommentDto {

	private final Long id;
	private final String comment;
	private final String author;
	private final LocalDate creationDate;

}
