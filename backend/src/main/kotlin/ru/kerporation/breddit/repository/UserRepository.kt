package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
	fun findByUsername(username: String): User?
}
