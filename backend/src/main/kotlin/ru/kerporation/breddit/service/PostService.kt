package ru.kerporation.breddit.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.converter.PostConverter
import ru.kerporation.breddit.dto.PostRequest
import ru.kerporation.breddit.dto.PostResponse
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.repository.PostRepository
import ru.kerporation.breddit.repository.SubredditRepository
import ru.kerporation.breddit.repository.UserRepository
import ru.kerporation.breddit.utils.toNullable

@Service
class PostService(
	private val postRepository: PostRepository,
	private val subredditRepository: SubredditRepository,
	private val userRepository: UserRepository,
	private val authService: AuthService,
	private val postConverter: PostConverter
) {

	@Transactional
	fun save(postRequest: PostRequest) {
		val subreddit: Subreddit = checkNotNull(subredditRepository.findByName(postRequest.subredditName)) { "Subreddit ${postRequest.subredditName} не найден " }
		postRepository.save(postConverter.toEntity(postRequest, subreddit, authService.getCurrentUser()))
	}

	@Transactional(readOnly = true)
	fun getPost(id: Long): PostResponse {
		val post: Post = checkNotNull(postRepository.findById(id).toNullable()) { "Пост с id $id не найден" }
		return postConverter.toPostResponse(post)
	}

	@Transactional(readOnly = true)
	fun getAllPosts(): List<PostResponse> {
		return postRepository.findAll()
			.map(postConverter::toPostResponse)
			.toList()
	}

	@Transactional(readOnly = true)
	fun getPostsBySubreddit(subredditId: Long): List<PostResponse> {
		val subreddit: Subreddit = checkNotNull(subredditRepository.findById(subredditId).toNullable()) { "Subreddit $subredditId не найден " }
		val posts: List<Post> = postRepository.findAllBySubreddit(subreddit)
		return posts
			.map(postConverter::toPostResponse)
			.toList()
	}

	@Transactional(readOnly = true)
	fun getPostsByUsername(username: String): List<PostResponse> {
		val user: User = checkNotNull(userRepository.findByUsername(username)) { "Пользователь $username не найден" }
		return postRepository.findByUser(user)
			.map(postConverter::toPostResponse)
			.toList()
	}

}
