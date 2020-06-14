package ru.kerporation.breddit.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

	@ExceptionHandler(RuntimeException::class)
	fun interceptRuntimeException(runtimeException: RuntimeException): ResponseEntity<ErrorResponse> {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse(runtimeException.message))
	}

}
