package ru.kerporation.breddit.dto

import javax.validation.constraints.NotBlank

data class LogoutRequest(
	@NotBlank val refreshToken: String
)
