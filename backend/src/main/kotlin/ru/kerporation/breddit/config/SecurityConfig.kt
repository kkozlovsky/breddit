package ru.kerporation.breddit.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.kerporation.breddit.security.JwtAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
	@Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService,
	private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {

	override fun configure(httpSecurity: HttpSecurity) {
		httpSecurity.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/auth/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/subreddit")
			.permitAll()
			.antMatchers("/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**")
			.permitAll()
			.anyRequest()
			.authenticated()

		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
	}

	@Autowired
	fun configureGlobal(authenticationManagerBuilder: AuthenticationManagerBuilder) {
		authenticationManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder())
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	override fun authenticationManagerBean(): AuthenticationManager {
		return super.authenticationManagerBean()
	}
}
