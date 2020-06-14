package ru.kerporation.breddit.exception

class BredditRuntimeException : RuntimeException {
	constructor(message: String) : super(message)
	constructor(message: String, ex: Exception) : super(message, ex)
	constructor(ex: Exception) : super(ex)
}
