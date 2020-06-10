package ru.kerporation.breddit.dto

import ru.kerporation.breddit.model.VoteType

data class VoteDto(
	val voteType: VoteType,
	val postId: Long
)
