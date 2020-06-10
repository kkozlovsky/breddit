package ru.kerporation.breddit.converter

import com.github.marlonlom.utilities.timeago.TimeAgo
import org.springframework.stereotype.Component
import ru.kerporation.breddit.dto.PostRequest
import ru.kerporation.breddit.dto.PostResponse
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.repository.CommentRepository

@Component
class PostConverter(
	private val commentRepository: CommentRepository
) {

	fun toPostResponse(post: Post): PostResponse {
		return PostResponse(
			checkNotNull(post.id),
			post.postName,
			post.url,
			post.description,
			post.user.username,
			post.subreddit?.name,
			post.voteCount,
			commentRepository.findByPost(post).size,
			TimeAgo.using(post.created.toEpochMilli())
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

}
