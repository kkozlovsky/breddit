package ru.kerporation.breddit.converter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.kerporation.breddit.dto.SubredditDto
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User

class SubredditConverterTest {

	private val subredditConverter: SubredditConverter = SubredditConverter()

	@Test
	fun toDto() {
		val subreddit = Subreddit().apply {
			id = 1L
			name = "Test subreddit"
			user = User().apply {
				username = "testuser"
				password = "testpass"
				email = "test@mail.com"
			}
			description = "Test description"
		}
		val subredditDto: SubredditDto = subredditConverter.toDto(subreddit)
		assertEquals(subredditDto.id, 1L)
		assertEquals(subredditDto.name, "Test subreddit")
		assertEquals(subredditDto.description, "Test description")
		assertEquals(subredditDto.numberOfPosts, 0)

	}

	@Test
	fun toEntity() {
		val subredditDto = SubredditDto(null, "Test subreddit", "Test description")
		val user = User().apply {
			username = "testuser"
			password = "testpass"
			email = "test@mail.com"
		}
		val subreddit = subredditConverter.toEntity(subredditDto, user)
		assertEquals(subreddit.name, "Test subreddit")
		assertEquals(subreddit.description, "Test description")
		assertNull(subreddit.id)
		assertEquals(subreddit.posts.size, 0)
		assertEquals(subreddit.user.username, "testuser")
	}
}
