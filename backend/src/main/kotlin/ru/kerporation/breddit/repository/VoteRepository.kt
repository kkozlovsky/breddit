package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.model.Vote

@Repository
interface VoteRepository : JpaRepository<Vote, Long> {
	fun findTopByPostAndUserOrderByIdDesc(post: Post, currentUser: User): Vote?
}
