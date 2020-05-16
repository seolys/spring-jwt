package seol.study.secr.security.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import seol.study.secr.security.AccountContext
import seol.study.secr.security.JwtDecoder
import seol.study.secr.security.token.JwtPreProcessingToken
import seol.study.secr.security.token.PostAuthorizationToken

@Component
open class JwtAuthenticationProvider: AuthenticationProvider {

    @Autowired
    lateinit var jwtDecoder: JwtDecoder

    override fun supports(authentication: Class<*>?): Boolean {
        return JwtPreProcessingToken::class.java.isAssignableFrom(authentication)
    }

    override fun authenticate(authentication: Authentication?): Authentication {
        val preToken: JwtPreProcessingToken = authentication as JwtPreProcessingToken
        val token = preToken.principal as String

        val context: AccountContext = jwtDecoder.decodeJwt(token)
        println(context.authorities)
        println(context.authorities)
        println(context.authorities)
        println(context.authorities)
        return PostAuthorizationToken.getTokenFromAccountContext(context)
    }


}