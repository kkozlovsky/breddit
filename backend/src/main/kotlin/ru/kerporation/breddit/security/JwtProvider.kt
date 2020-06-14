package ru.kerporation.breddit.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.parserBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import ru.kerporation.breddit.exception.BredditRuntimeException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.PrivateKey
import java.security.PublicKey
import java.sql.Date
import java.time.Instant
import javax.annotation.PostConstruct


@Service
class JwtProvider(
	@Value("\${jwt.expiration.time}") private val jwtExpirationInMillis: Long
) {

	lateinit var keyStore: KeyStore

	@PostConstruct
	fun init() {
		try {
			keyStore = KeyStore.getInstance("JKS")
			val resourceAsStream = javaClass.getResourceAsStream("/breddit.jks")
			keyStore.load(resourceAsStream, "secret".toCharArray())
		} catch (e: Exception) {
			throw BredditRuntimeException("Ошибка ининициализации хранилища:", e)
		}
	}

	fun generateToken(authentication: Authentication): String {
		val principal = authentication.principal as User
		return Jwts.builder()
			.setSubject(principal.username)
			.setIssuedAt(Date.from(Instant.now()))
			.signWith(getPrivateKey())
			.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
			.compact()
	}

	fun generateTokenWithUserName(username: String): String {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(Date.from(Instant.now()))
			.signWith(getPrivateKey())
			.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
			.compact()
	}

	private fun getPrivateKey(): PrivateKey {
		return try {
			keyStore.getKey("breddit", "secret".toCharArray()) as PrivateKey
		} catch (e: Exception) {
			throw BredditRuntimeException("Ошибка при получении приватного ключа", e)
		}
	}

	fun validateToken(jwt: String): Boolean {
		parserBuilder().setSigningKey(getPublickey()).build().parseClaimsJws(jwt)
		return true
	}

	private fun getPublickey(): PublicKey {
		return try {
			keyStore.getCertificate("breddit").publicKey
		} catch (e: KeyStoreException) {
			throw BredditRuntimeException("Ошибка при получении публичного ключа", e)
		}
	}

	fun getUsernameFromJwt(token: String): String {
		val claims = parserBuilder()
			.setSigningKey(getPublickey())
			.build()
			.parseClaimsJws(token)
			.body
		return claims.subject
	}

	fun getJwtExpirationInMillis(): Long {
		return jwtExpirationInMillis
	}

}
