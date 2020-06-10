package ru.kerporation.breddit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kerporation.breddit.dto.RegisterRequest
import ru.kerporation.breddit.service.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
	private val authService: AuthService
) {

	@PostMapping("/signup")
	fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
		authService.signup(registerRequest)
		return ResponseEntity("Регистрация прошла успешно", HttpStatus.OK)
	}

	@GetMapping("accountVerification/{token}")
	fun verifyAccount(@PathVariable token: String): ResponseEntity<String> {
		authService.verifyAccount(token)
		return ResponseEntity("Аккаунт успешно активирован", HttpStatus.OK)
	}

}
