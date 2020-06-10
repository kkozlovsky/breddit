package ru.kerporation.breddit.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfiguration() {
	@Bean
	fun redditCloneApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo)
	}

	private val apiInfo: ApiInfo
		private get() = ApiInfoBuilder()
			.title("Breddit API")
			.version("1.0")
			.description("API for Reddit Clone Application")
			.license("Apache License Version 2.0")
			.build()
}
