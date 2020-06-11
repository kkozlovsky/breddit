package ru.kerporation.breddit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync
import ru.kerporation.breddit.config.SwaggerConfiguration

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration::class)
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
