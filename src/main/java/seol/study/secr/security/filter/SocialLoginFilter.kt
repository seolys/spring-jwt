package seol.study.secr.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import seol.study.secr.dto.SocialLoginDto
import seol.study.secr.security.token.PreAuthorizationToken
import seol.study.secr.security.token.SocialPreAuthorizationToken
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SocialLoginFilter(defaultFilterProcessesUrl: String?) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(req: HttpServletRequest?, res: HttpServletResponse?): Authentication {
        val objectMapper = ObjectMapper()
        val dto: SocialLoginDto = objectMapper.readValue(req!!.reader, SocialLoginDto::class.java)
        return super.getAuthenticationManager().authenticate(SocialPreAuthorizationToken(dto))
    }

}