package ru.kerporation.breddit.converter

import org.springframework.stereotype.Component
import ru.kerporation.breddit.dto.SubredditDto
import ru.kerporation.breddit.model.Subreddit

@Component
class SubredditConverter {

	fun toDto(subreddit: Subreddit): SubredditDto {
		return SubredditDto(
			subreddit.id,
			subreddit.name,
			subreddit.description,
			subreddit.posts.size
		)
	}

	fun toEntity(subredditDto: SubredditDto): Subreddit {
		return Subreddit().apply {
			this.name = subredditDto.name
			this.description = subredditDto.description
		}
	}

}
