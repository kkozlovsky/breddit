package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.AuthenticationResponse
import ru.kerporation.breddit.dto.LoginRequest
import ru.kerporation.breddit.dto.RefreshTokenRequest
import ru.kerporation.breddit.dto.RegisterRequest
import ru.kerporation.breddit.service.AuthService
import ru.kerporation.breddit.service.RefreshTokenService
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
	private val authService: AuthService,
	private val refreshTokenService: RefreshTokenService
) {

	@PostMapping("/signup")
	fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
		authService.signup(registerRequest)
		return ResponseEntity("Регистрация прошла успешно", HttpStatus.OK)
	}

	@GetMapping("/accountVerification/{token}")
	fun verifyAccount(@PathVariable token: String): ResponseEntity<String> {
		authService.verifyAccount(token)
		return ResponseEntity("Аккаунт успешно активирован", HttpStatus.OK)
	}

	@PostMapping("/login")
	fun login(@RequestBody loginRequest: LoginRequest): AuthenticationResponse {
		return authService.login(loginRequest)
	}

	@PostMapping("/refresh/token")
	fun refreshTokens(@RequestBody refreshTokenRequest: @Valid RefreshTokenRequest): AuthenticationResponse {
		return authService.refreshToken(refreshTokenRequest)
	}

	@PostMapping("/logout")
	fun logout(@RequestBody refreshTokenRequest: @Valid RefreshTokenRequest): ResponseEntity<String> {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.refreshToken)
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token успешно удалён.")
	}

}
