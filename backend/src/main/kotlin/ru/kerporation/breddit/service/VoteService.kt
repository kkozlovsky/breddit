package ru.kerporation.breddit.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.dto.VoteDto
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Vote
import ru.kerporation.breddit.model.VoteType
import ru.kerporation.breddit.repository.PostRepository
import ru.kerporation.breddit.repository.VoteRepository
import ru.kerporation.breddit.utils.toNullable

@Service
class VoteService(
	private val voteRepository: VoteRepository,
	private val postRepository: PostRepository,
	private val authService: AuthService
) {


	@Transactional
	fun vote(voteDto: VoteDto) {
		val post: Post? = postRepository.findById(voteDto.postId).toNullable()
		checkNotNull(post) { "Пост с id ${voteDto.postId} не найден" }

		val voteByPostAndUser: Vote? = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser())
		voteByPostAndUser?.let { it ->
			if (it.voteType == voteDto.voteType) {
				throw RuntimeException("You have already ${voteDto.voteType}'d for this post")
			}
		}

		if (VoteType.UPVOTE == voteDto.voteType) {
			post.voteCount = post.voteCount + 1
		} else {
			post.voteCount = post.voteCount - 1
		}
		voteRepository.save(Vote().apply {
			this.voteType = voteDto.voteType
			this.post = post
			this.user = authService.getCurrentUser()
		})

		postRepository.save(post)
	}
}
