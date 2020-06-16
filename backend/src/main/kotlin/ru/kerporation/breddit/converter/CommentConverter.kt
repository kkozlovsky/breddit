package ru.kerporation.breddit.converter


import com.github.marlonlom.utilities.timeago.TimeAgo
import org.springframework.stereotype.Component
import ru.kerporation.breddit.dto.CommentsDto
import ru.kerporation.breddit.model.Comment
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.User

@Component
class CommentConverter {

	fun toDto(comment: Comment): CommentsDto {
		return CommentsDto(
			checkNotNull(comment.id),
			checkNotNull(comment.post.id),
			comment.created,
			comment.text,
			comment.user.username,
			TimeAgo.using(comment.created.toEpochMilli())
		)
	}

	fun toEntity(commentDto: CommentsDto, post: Post, user: User): Comment {
		return Comment().apply {
			this.post = post
			this.user = user
			this.text = commentDto.text
		}
	}

}

