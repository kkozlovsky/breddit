package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.CommentsDto
import ru.kerporation.breddit.service.CommentService

@RestController
@RequestMapping("/api/comments")
class CommentsController(
	private val commentService: CommentService
) {

	@PostMapping(
		produces = [MediaType.APPLICATION_JSON_VALUE],
		consumes = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun createComment(@RequestBody commentsDto: CommentsDto): ResponseEntity<Void> {
		commentService.save(commentsDto)
		return ResponseEntity(HttpStatus.CREATED)
	}

	@GetMapping(
		value = ["/by-post/{postId}"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getAllCommentsForPost(@PathVariable postId: Long): ResponseEntity<List<CommentsDto>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(commentService.getAllCommentsForPost(postId))
	}

	@GetMapping(
		value = ["/by-user/{username}"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getAllCommentsForUser(@PathVariable username: String): ResponseEntity<List<CommentsDto>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(commentService.getAllCommentsForUser(username))
	}
}
