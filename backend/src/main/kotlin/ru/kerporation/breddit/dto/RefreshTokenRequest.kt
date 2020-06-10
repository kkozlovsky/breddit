package ru.kerporation.breddit.dto

import javax.validation.constraints.NotBlank

data class RefreshTokenRequest(
	@NotBlank
	val refreshToken: String,
	val username: String
)
