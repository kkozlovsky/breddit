package ru.kerporation.breddit.dto

data class NotificationEmail(
	val subject: String,
	val recipient: String,
	val body: String
)
