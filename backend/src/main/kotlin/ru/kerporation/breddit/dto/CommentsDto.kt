package ru.kerporation.breddit.dto

import java.time.Instant

data class CommentsDto(
	val id: Long?,
	val postId: Long,
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	val createdDate: Instant = Instant.now(),
	val text: String,
	val username: String
)
