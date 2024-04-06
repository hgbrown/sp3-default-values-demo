package dev.hbrown.demo.greeting

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig(
    val salutation: String = "Hello",
)
