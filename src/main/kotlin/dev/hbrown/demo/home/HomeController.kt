package dev.hbrown.demo.home

import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    private val env: Environment,
    private val bp: BuildProperties,
) {
    @GetMapping(
        path = ["/version"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun version(): String = """
        {
            "version": "${bp.version}",
            "time": "${bp.time}",
            "name": "${bp.name}",
            "profiles": "${env.activeProfiles.joinToString(separator = ",")}"
        }
    """.trimIndent()

    @GetMapping(
        path = ["/health"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun health(): String = """
        {
            "status": "UP" 
        }
    """.trimIndent()
}
