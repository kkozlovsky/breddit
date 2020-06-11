package ru.kerporation.breddit.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
	private val jwtProvider: JwtProvider,
	@Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

	override fun doFilterInternal(request: HttpServletRequest,
								  response: HttpServletResponse,
								  filterChain: FilterChain) {

		val jwt = getJwtFromRequest(request)
		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
			val username = jwtProvider.getUsernameFromJwt(jwt)
			val userDetails = userDetailsService.loadUserByUsername(username)
			val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
			authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
			SecurityContextHolder.getContext().authentication = authentication
		}
		filterChain.doFilter(request, response)
	}

	private fun getJwtFromRequest(request: HttpServletRequest): String {
		val bearerToken = request.getHeader("Authorization")
		return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			bearerToken.substring(7)
		} else bearerToken
	}
}
