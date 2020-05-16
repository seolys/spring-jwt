package seol.study.secr.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import seol.study.secr.LoggerDelegate

@Component
open class JwtFactory {
    val logger by LoggerDelegate()

    companion object {
        private val signingKey: String = "jwttest"
    }

    fun generateToken(context: AccountContext) : String {
        val authorities: MutableCollection<GrantedAuthority> = context.authorities
        try {
            val token: String = JWT.create()
                    .withIssuer("seolnavy")
                    .withClaim("USER_NAME", context.username)
                    .withClaim("USER_ROLE", authorities.first().authority)
                    .sign(generateAlgorithm())
            return token
        } catch (e: Exception) {
            logger.error(e.message)
            throw e
        }
    }

    private fun generateAlgorithm() : Algorithm {
        return Algorithm.HMAC256(signingKey)
    }

}