package seol.study.secr.security.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import seol.study.secr.LoggerDelegate
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFailureHandler: AuthenticationFailureHandler {
    val log by LoggerDelegate()

    override fun onAuthenticationFailure(req: HttpServletRequest?, res: HttpServletResponse?, exception: AuthenticationException?) {
        log.error("인증 실패" + (exception?.message ?: ""))
    }

}