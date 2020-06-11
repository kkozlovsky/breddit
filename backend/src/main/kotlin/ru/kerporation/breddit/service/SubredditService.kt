package ru.kerporation.breddit.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.converter.SubredditConverter
import ru.kerporation.breddit.dto.SubredditDto
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.repository.SubredditRepository
import ru.kerporation.breddit.utils.toNullable

@Service
class SubredditService(
	private val subredditRepository: SubredditRepository,
	private val subredditConverter: SubredditConverter
) {

	@Transactional
	fun save(subredditDto: SubredditDto): SubredditDto {
		val newSubreddit: Subreddit = subredditRepository.save(subredditConverter.toEntity(subredditDto))
		return subredditConverter.toDto(newSubreddit)
	}

	@Transactional(readOnly = true)
	fun getAll(): List<SubredditDto> {
		return subredditRepository.findAll()
			.map(subredditConverter::toDto)
			.toList()
	}

	@Transactional(readOnly = true)
	fun getSubreddit(id: Long): SubredditDto {
		val subreddit: Subreddit = checkNotNull(subredditRepository.findById(id).toNullable()) { "Subreddit c id $id не найден" }
		return subredditConverter.toDto(subreddit)
	}

}
