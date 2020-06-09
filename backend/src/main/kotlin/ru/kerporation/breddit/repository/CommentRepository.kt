package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.Comment
import ru.kerporation.breddit.model.Post
import ru.kerporation.breddit.model.User

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
	fun findByPost(post: Post): List<Comment>
	fun findAllByUser(user: User): List<Comment>
}
