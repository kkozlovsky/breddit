package ru.kerporation.breddit.dto

data class PostRequest(
	val id: Long,
	val subredditName: String = "",
	val postName: String,
	val url: String = "",
	val description: String
)
