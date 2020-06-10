package ru.kerporation.breddit.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.kerporation.breddit.dto.NotificationEmail
import ru.kerporation.breddit.dto.RegisterRequest
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.model.VerificationToken
import ru.kerporation.breddit.repository.UserRepository
import ru.kerporation.breddit.repository.VerificationTokenRepository
import java.util.*

@Service
class AuthService(
	private val passwordEncoder: PasswordEncoder,
	private val userRepository: UserRepository,
	private val verificationTokenRepository: VerificationTokenRepository,
	private val mailService: MailService
) {

	fun signup(registerRequest: RegisterRequest) {
		val user = User()
			.apply {
				username = registerRequest.username
				email = registerRequest.email
				password = passwordEncoder.encode(registerRequest.password)
			}.let {
				userRepository.save(it)
			}
		val verificationToken = generateVerificationToken(user)

		mailService.sendMail(NotificationEmail(
			"Пожалуйста, активируйте аккаунт",
			user.email,
			"Спасибо за регистрацию, пройдите по ссылке и подтверите аккаунт: " +
				"http://localhost:8080/api/auth/accountVerification/$verificationToken"
		))
	}

	private fun generateVerificationToken(user: User): String {
		val token: String = UUID.randomUUID().toString()
		VerificationToken()
			.apply {
				this.token = token
				this.user = user
			}.let {
				verificationTokenRepository.save(it)
			}
		return token
	}

	fun verifyAccount(token: String) {
		val verificationToken: VerificationToken? = verificationTokenRepository.findByToken(token)
		checkNotNull(verificationToken) { "Invalid Token" }
		fetchUserAndEnable(verificationToken)
	}

	private fun fetchUserAndEnable(verificationToken: VerificationToken) {
		val username: String = verificationToken.user.username
		val user: User? = userRepository.findByUsername(username)
		checkNotNull(user) { "Пользователь $username не найден" }
		user.enabled = true
		userRepository.save(user)
	}
}
