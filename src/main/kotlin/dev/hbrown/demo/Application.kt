package dev.hbrown.demo

import dev.hbrown.demo.greeting.GreetingConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment
import kotlin.time.toKotlinDuration

@SpringBootApplication
@EnableConfigurationProperties(
    GreetingConfig::class
)
class Application(
    private val env: Environment,
    private val build: BuildProperties,
    private val greetingConfig: GreetingConfig,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent) {
        println("=== <APPLICATION_READY> ===")
        println("Time taken=[${event.timeTaken.toKotlinDuration()}]")
        println("Build version=[${build.version}]")
        println("Build time=[${build.time}]")
        println("Active profiles=[${env.activeProfiles.joinToString()}]")
        println("Greeting Config=[$greetingConfig]")
        println("=== </APPLICATION_READY> ===")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
