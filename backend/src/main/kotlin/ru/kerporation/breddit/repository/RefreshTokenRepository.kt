package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.RefreshToken

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
	fun findByToken(token: String): RefreshToken?
	fun deleteByToken(token: String)
}
