package ru.kerporation.breddit.dto

data class RegisterRequest(
	val email: String,
	val username: String,
	val password: String
)
