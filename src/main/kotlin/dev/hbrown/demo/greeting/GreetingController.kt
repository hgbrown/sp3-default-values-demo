package dev.hbrown.demo.greeting

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController(
    val greetingConfig: GreetingConfig,
) {

    @GetMapping(
        path = ["/greet"],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun greet(): String = "${greetingConfig.salutation}, World!"

}
