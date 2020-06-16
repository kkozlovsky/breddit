package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.Subreddit


@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long> {
	fun findByName(subredditName: String): Subreddit?
}
