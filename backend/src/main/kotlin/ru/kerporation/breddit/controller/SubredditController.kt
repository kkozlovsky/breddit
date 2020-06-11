package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.SubredditDto
import ru.kerporation.breddit.service.SubredditService

@RestController
@RequestMapping("/api/subreddit")
class SubredditController(
	private val subredditService: SubredditService
) {

	@PostMapping
	fun createSubreddit(@RequestBody subredditDto: SubredditDto): ResponseEntity<SubredditDto> {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(subredditService.save(subredditDto))
	}

	@GetMapping
	fun getAllSubreddits(): ResponseEntity<List<SubredditDto>> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(subredditService.getAll())
	}

	@GetMapping("/{id}")
	fun getSubreddit(@PathVariable id: Long): ResponseEntity<SubredditDto> {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(subredditService.getSubreddit(id))
	}

}
