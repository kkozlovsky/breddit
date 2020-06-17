package ru.kerporation.breddit.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.converter.CommentConverter
import ru.kerporation.breddit.dto.CommentDto
import ru.kerporation.breddit.dto.NotificationEmail
import ru.kerporation.breddit.model.Comment
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.repository.CommentRepository
import ru.kerporation.breddit.repository.PostRepository
import ru.kerporation.breddit.repository.UserRepository
import ru.kerporation.breddit.utils.toNullable

@Service
class CommentService(
	private val postRepository: PostRepository,
	private val userRepository: UserRepository,
	private val authService: AuthService,
	private val commentConverter: CommentConverter,
	private val commentRepository: CommentRepository,
	private val mailContentBuilder: MailContentBuilder,
	private val mailService: MailService
) {

	@Transactional
	fun save(commentDto: CommentDto) {
		val post: Post = checkNotNull(postRepository.findById(commentDto.postId).toNullable()) { "Пост с id ${commentDto.postId} не найден" }
		val comment: Comment = commentConverter.toEntity(commentDto, post, authService.getCurrentUser())
		commentRepository.save(comment)
		val message = mailContentBuilder.build(post.user.username + " прокомментировал ваш пост.")
		sendCommentNotification(message, post.user)
	}

	@Transactional(readOnly = true)
	fun getAllCommentsForPost(postId: Long): List<CommentDto> {
		val post: Post = checkNotNull(postRepository.findById(postId).toNullable()) { "Пост с id $postId не найден" }
		return commentRepository.findByPost(post)
			.map(commentConverter::toDto)
			.toList()
	}

	@Transactional(readOnly = true)
	fun getAllCommentsForUser(username: String): List<CommentDto> {
		val user: User = checkNotNull(userRepository.findByUsername(username)) { "Пользователь $username не найден" }
		return commentRepository.findAllByUser(user)
			.map(commentConverter::toDto)
			.toList()
	}


	private fun sendCommentNotification(message: String, user: User) {
		mailService.sendMail(NotificationEmail(user.username + " прокомментировал ваш пост", user.email, message))
	}

}
