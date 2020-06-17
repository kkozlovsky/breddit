package ru.kerporation.breddit.converter

import org.junit.jupiter.api.Assertions.assertTrue
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
		assertTrue(subredditDto.id == 1L)
		assertTrue(subredditDto.name == "Test subreddit")
		assertTrue(subredditDto.description == "Test description")
		assertTrue(subredditDto.numberOfPosts == 0)

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
		assertTrue(subreddit.name == "Test subreddit")
		assertTrue(subreddit.description == "Test description")
		assertTrue(subreddit.id == null)
		assertTrue(subreddit.posts.size == 0)
		assertTrue(subreddit.user.username == "testuser")
	}
}
