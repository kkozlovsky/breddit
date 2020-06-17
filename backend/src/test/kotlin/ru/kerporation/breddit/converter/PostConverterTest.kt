package ru.kerporation.breddit.converter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ru.kerporation.breddit.dto.PostRequest
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.repository.CommentRepository
import ru.kerporation.breddit.repository.VoteRepository
import ru.kerporation.breddit.service.AuthService

class PostConverterTest {

	private val commentRepositoryMock: CommentRepository = Mockito.mock(CommentRepository::class.java)
	private val authServiceMock: AuthService = Mockito.mock(AuthService::class.java)
	private val voteRepositoryMock: VoteRepository = Mockito.mock(VoteRepository::class.java)
	private val postConverter = PostConverter(commentRepositoryMock, authServiceMock, voteRepositoryMock)

	@Test
	fun toPostResponse() {
		Mockito.`when`(authServiceMock.isLoggedIn()).thenReturn(false)

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
				user = User().apply {
					username = "subredditUser"
					password = "testpass"
					email = "test@mail.com"
				}
			}

		}
		val postResponse = postConverter.toPostResponse(post)
		assertEquals(postResponse.id, 19L)
		assertEquals(postResponse.postName, "Test post")
		assertEquals(postResponse.url, "github.com")
		assertEquals(postResponse.username, "postUser")
		assertEquals(postResponse.subredditName, "Test subreddit")
		assertEquals(postResponse.voteCount, 7L)
		assertEquals(postResponse.commentCount, 0)
		assertEquals(postResponse.duration, "just now")
		assertFalse(postResponse.upVote)
		assertFalse(postResponse.downVote)
		Mockito.verify(authServiceMock, Mockito.times(2)).isLoggedIn()
	}

	@Test
	fun toEntity() {
		val postRequest = PostRequest(null, "Test subreddit", "Test post", "github.com", "Test description")
		val user = User().apply {
			username = "postUser"
			password = "testpass"
			email = "test2@mail.com"
		}
		val subreddit = Subreddit().apply {
			id = 1L
			name = "Test subreddit"
			description = "Test description"
			this.user = User().apply {
				username = "subredditUser"
				password = "testpass"
				email = "test@mail.com"
			}
		}

		val post = postConverter.toEntity(postRequest, subreddit, user)
		assertNull(post.id)
		assertEquals(post.description, "Test description")
		assertEquals(post.subreddit.name, "Test subreddit")
		assertEquals(post.user.username, "postUser")
		assertEquals(post.postName, "Test post")
		assertEquals(post.url, "github.com")
		assertEquals(post.voteCount, 0L)
	}
}
