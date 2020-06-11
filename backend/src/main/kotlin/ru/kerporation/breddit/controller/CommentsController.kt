package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.CommentsDto
import ru.kerporation.breddit.service.CommentService

@RestController
@RequestMapping("/api/comments")
class CommentsController(
	private val commentService: CommentService
) {

	@PostMapping
	fun createComment(@RequestBody commentsDto: CommentsDto): ResponseEntity<Void> {
		commentService.save(commentsDto)
		return ResponseEntity(HttpStatus.CREATED)
	}

	@GetMapping("/by-post/{postId}")
	fun getAllCommentsForPost(@PathVariable postId: Long): ResponseEntity<List<CommentsDto>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(commentService.getAllCommentsForPost(postId))
	}

	@GetMapping("/by-user/{username}")
	fun getAllCommentsForUser(@PathVariable username: String): ResponseEntity<List<CommentsDto>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(commentService.getAllCommentsForUser(username))
	}
}
