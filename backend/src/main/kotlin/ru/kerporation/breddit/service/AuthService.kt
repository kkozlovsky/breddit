package ru.kerporation.breddit.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kerporation.breddit.dto.*
import ru.kerporation.breddit.model.User
import ru.kerporation.breddit.model.VerificationToken
import ru.kerporation.breddit.repository.UserRepository
import ru.kerporation.breddit.repository.VerificationTokenRepository
import ru.kerporation.breddit.security.JwtProvider
import java.time.Instant
import java.util.*

@Service
@Transactional
class AuthService(
	private val passwordEncoder: PasswordEncoder,
	private val userRepository: UserRepository,
	private val verificationTokenRepository: VerificationTokenRepository,
	private val mailService: MailService,
	private val authenticationManager: AuthenticationManager,
	private val jwtProvider: JwtProvider,
	private val refreshTokenService: RefreshTokenService
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

	@Transactional(readOnly = true)
	fun getCurrentUser(): User {
		val principal = SecurityContextHolder.getContext().authentication.principal as org.springframework.security.core.userdetails.User
		return checkNotNull(userRepository.findByUsername(principal.username)) { "Пользователь ${principal.username} не найден" }
	}

	fun verifyAccount(token: String) {
		val verificationToken: VerificationToken? = verificationTokenRepository.findByToken(token)
		checkNotNull(verificationToken) { "Invalid Token" }
		fetchUserAndEnable(verificationToken)
	}

	fun login(loginRequest: LoginRequest): AuthenticationResponse {
		val authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
		SecurityContextHolder.getContext().authentication = authenticate
		val token = jwtProvider.generateToken(authenticate)
		return AuthenticationResponse(
			token,
			refreshTokenService.generateRefreshToken().token,
			Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
			loginRequest.username
		)
	}

	fun refreshToken(refreshTokenRequest: RefreshTokenRequest): AuthenticationResponse {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.refreshToken)
		val token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.username)
		return AuthenticationResponse(
			token,
			refreshTokenRequest.refreshToken,
			Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
			refreshTokenRequest.username
		)
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

	private fun fetchUserAndEnable(verificationToken: VerificationToken) {
		val username: String = verificationToken.user.username
		val user: User? = userRepository.findByUsername(username)
		checkNotNull(user) { "Пользователь $username не найден" }
		user.enabled = true
		userRepository.save(user)
	}
}
