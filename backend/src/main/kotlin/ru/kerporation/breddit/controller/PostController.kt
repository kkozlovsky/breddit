package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
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

	@PostMapping
	fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Void> {
		postService.save(postRequest)
		return ResponseEntity(HttpStatus.CREATED)
	}

	@GetMapping
	fun getAllPosts(): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getAllPosts())
	}

	@GetMapping("/{id}")
	fun getPost(@PathVariable id: Long): ResponseEntity<PostResponse> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPost(id))
	}

	@GetMapping("/by-subreddit/{id}")
	fun getPostsBySubreddit(id: Long): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPostsBySubreddit(id))
	}

	@GetMapping("/by-user/{name}")
	fun getPostsByUsername(username: String): ResponseEntity<List<PostResponse>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(postService.getPostsByUsername(username))
	}

}
