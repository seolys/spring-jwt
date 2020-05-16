package seol.study.secr.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Component
import seol.study.secr.LoggerDelegate
import seol.study.secr.security.exception.InvalidJwtException
import java.util.*


@Component
open class JwtDecoder {
    val logger by LoggerDelegate()

    fun decodeJwt(token: String) : AccountContext {
        val decodedJwt = isValidToken(token)
                            .orElseThrow { InvalidJwtException("유효한 토큰이 아닙니다.") }

        val username = decodedJwt.getClaim("USER_NAME").asString()
        val userRole = decodedJwt.getClaim("USER_ROLE").asString()
        return AccountContext(username, "JWT_TOKEN", userRole)
    }

    private fun isValidToken(token: String) : Optional<DecodedJWT> {
        logger.info("token : " + token)
        var jwt: DecodedJWT? = null
        try {
            val algorithm: Algorithm = Algorithm.HMAC256("jwttest")
            val verifier = JWT.require(algorithm)
                    .withIssuer("seolnavy")
                    .build() //Reusable verifier instance
            jwt = verifier.verify(token)
            logger.info("token verify 성공")
        } catch (e: Exception) {
            logger.error("isValidToken Error : " + e.message, e)
        }
        return Optional.ofNullable(jwt)
    }

}