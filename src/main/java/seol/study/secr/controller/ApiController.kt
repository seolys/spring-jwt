package seol.study.secr.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seol.study.secr.LoggerDelegate
import seol.study.secr.security.token.PostAuthorizationToken

@RestController
@RequestMapping("/api")
open class ApiController {
    val log by LoggerDelegate()

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    fun getUsername(authentication: Authentication) : String {
        println("/api/hello")
        println("/api/hello")
        println("/api/hello")

        val token = authentication as PostAuthorizationToken
//        println("token : " + token)
//        println("token.principal = ${token.principal}")
//        println("token.name = ${token.name}")
        return token.name
//        return "{\"key\": \"12345\"}"
    }

}