package ru.kerporation.breddit.converter

import com.github.marlonlom.utilities.timeago.TimeAgo
import org.springframework.stereotype.Component
import ru.kerporation.breddit.dto.PostRequest
import ru.kerporation.breddit.dto.PostResponse
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.model.VoteType
import ru.kerporation.breddit.repository.CommentRepository
import ru.kerporation.breddit.repository.VoteRepository
import ru.kerporation.breddit.service.AuthService

@Component
class PostConverter(
	private val commentRepository: CommentRepository,
	private val authService: AuthService,
	private val voteRepository: VoteRepository
) {

	fun toPostResponse(post: Post): PostResponse {
		return PostResponse(
			checkNotNull(post.id),
			post.postName,
			post.url,
			post.description,
			post.user.username,
			post.subreddit?.name ?: "",
			post.voteCount,
			commentRepository.findByPost(post).size,
			TimeAgo.using(post.created.toEpochMilli()),
			isPostUpVoted(post),
			isPostDownVoted(post)
		)
	}

	fun toEntity(postRequest: PostRequest, subreddit: Subreddit, user: User): Post {
		return Post().apply {
			this.subreddit = subreddit
			this.user = user
			this.description = postRequest.description
			this.url = postRequest.url
			this.postName = postRequest.postName
		}
	}

	fun isPostUpVoted(post: Post): Boolean {
		return checkVoteType(post, VoteType.UPVOTE)
	}

	fun isPostDownVoted(post: Post): Boolean {
		return checkVoteType(post, VoteType.DOWNVOTE)
	}

	private fun checkVoteType(post: Post, voteType: VoteType): Boolean {
		if (authService.isLoggedIn()) {
			val voteForPostByUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser())
			if (voteForPostByUser != null && voteForPostByUser.voteType == voteType) {
				return true
			}
		}
		return false
	}

}
