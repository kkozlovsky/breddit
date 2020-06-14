package ru.kerporation.breddit.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.model.RefreshToken
import ru.kerporation.breddit.repository.RefreshTokenRepository
import java.util.*


@Service
@Transactional
class RefreshTokenService(
	private val refreshTokenRepository: RefreshTokenRepository
) {

	fun generateRefreshToken(): RefreshToken {
		return refreshTokenRepository.save(RefreshToken().apply { token = UUID.randomUUID().toString() })
	}

	fun validateRefreshToken(token: String) {
		refreshTokenRepository.findByToken(token) ?: throw RuntimeException("Неверный RefreshToken")
	}

	fun deleteRefreshToken(token: String) {
		refreshTokenRepository.deleteByToken(token)
	}
}
