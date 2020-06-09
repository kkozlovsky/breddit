package ru.kerporation.breddit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.kerporation.breddit.model.VerificationToken

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
	fun findByToken(token: String): VerificationToken?
}
