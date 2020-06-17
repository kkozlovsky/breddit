package ru.kerporation.breddit.dto

import java.time.Instant

data class CommentDto(
	val id: Long?,
	val postId: Long,
	val createdDate: Instant = Instant.now(),
	val text: String,
	val username: String,
	val duration: String? = null
)
