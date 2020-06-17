package ru.kerporation.breddit.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import ru.kerporation.breddit.dto.CommentDto
import ru.kerporation.breddit.model.Comment
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User

class CommentConverterTest {

	private val commentConverter = CommentConverter()

	@Test
	fun toDto() {
		val comment = Comment().apply {
			id = 10L
			text = "Test comment"
			user = User().apply {
				username = "commentUser"
				password = "testpass"
				email = "test3@mail.com"
			}
			post = Post().apply {
				this.postName = "Test post"
				this.url = "github.com"
				this.description = "Post description"
				this.voteCount = 7
				this.id = 19L
				this.user = User().apply {
					username = "postUser"
					password = "testpass"
					email = "test2@mail.com"
				}
				this.subreddit = Subreddit().apply {
					id = 1L
					name = "Test subreddit"
					description = "Test description"
					user = User().apply {
						username = "subredditUser"
						password = "testpass"
						email = "test@mail.com"
					}
				}

			}
		}
		val commentsDto = commentConverter.toDto(comment)
		assertEquals(commentsDto.id, 10L)
		assertEquals(commentsDto.postId, 19L)
		assertEquals(commentsDto.text, "Test comment")
		assertEquals(commentsDto.username, "commentUser")
		assertEquals(commentsDto.duration, "just now")
	}

	@Test
	fun toEntity() {

		val commentsDto = CommentDto(id = null, postId = 19L, text = "Test comment", username = "commentUser")
		val user = User().apply {
			username = "commentUser"
			password = "testpass"
			email = "test3@mail.com"
		}
		val post = Post().apply {
			this.postName = "Test post"
			this.url = "github.com"
			this.description = "Post description"
			this.voteCount = 7
			this.id = 19L
			this.user = User().apply {
				username = "postUser"
				password = "testpass"
				email = "test2@mail.com"
			}
			this.subreddit = Subreddit().apply {
				id = 1L
				name = "Test subreddit"
				description = "Test description"
				this.user = User().apply {
					username = "subredditUser"
					password = "testpass"
					email = "test@mail.com"
				}
			}

		}

		val comment = commentConverter.toEntity(commentsDto, post, user)
		assertNull(comment.id)
		assertEquals(comment.post.id, 19L)
		assertEquals(comment.post.postName, "Test post")
		assertEquals(comment.text, "Test comment")
		assertEquals(comment.user.username, "commentUser")
	}
}
