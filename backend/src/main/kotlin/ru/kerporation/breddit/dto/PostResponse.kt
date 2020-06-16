package ru.kerporation.breddit.dto

data class PostResponse(
	val id: Long,
	val postName: String,
	val url: String = "",
	val description: String,
	val username: String,
	val subredditName: String = "",
	val voteCount: Long,
	val commentCount: Int,
	val duration: String,
	val upVote: Boolean = false,
	val downVote: Boolean = false
)
