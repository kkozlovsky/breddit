package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kerporation.breddit.dto.VoteDto
import ru.kerporation.breddit.service.VoteService

@RestController
@RequestMapping("/api/votes")
class VoteController(
	private val voteService: VoteService
) {

	@PostMapping
	fun vote(@RequestBody voteDto: VoteDto): ResponseEntity<Void> {
		voteService.vote(voteDto)
		return ResponseEntity(HttpStatus.OK)
	}

}
