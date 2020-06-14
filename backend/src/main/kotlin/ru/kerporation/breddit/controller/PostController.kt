package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.PostRequest
import ru.kerporation.breddit.dto.PostResponse
import ru.kerporation.breddit.service.PostService

@RestController
@RequestMapping("/api/posts")
class PostController(
	private val postService: PostService
) {

	@PostMapping(
		produces = [MediaType.APPLICATION_JSON_VALUE],
		consumes = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Void> {
		postService.save(postRequest)
		return ResponseEntity(HttpStatus.CREATED)
	}

	@GetMapping(
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getAllPosts(): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getAllPosts())
	}

	@GetMapping(
		value = ["/{id}"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getPost(@PathVariable id: Long): ResponseEntity<PostResponse> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPost(id))
	}

	@GetMapping(
		value = ["/by-subreddit/{id}"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getPostsBySubreddit(@PathVariable id: Long): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPostsBySubreddit(id))
	}

	@GetMapping(
		value = ["/by-user/{username}"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getPostsByUsername(@PathVariable username: String): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPostsByUsername(username))
	}

}
