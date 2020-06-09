package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.Subreddit
import ru.kerporation.breddit.model.User

@Repository
interface PostRepository : JpaRepository<Post, Long> {
	fun findAllBySubreddit(subreddit: Subreddit): List<Post>
	fun findByUser(user: User): List<Post>
}
