package ru.kerporation.breddit.dto

data class SubredditDto(
	val id: Long?,
	val name: String,
	val description: String,
	val numberOfPosts: Int = 0
)
