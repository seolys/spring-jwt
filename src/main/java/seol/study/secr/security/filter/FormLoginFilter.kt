package seol.study.secr.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import seol.study.secr.LoggerDelegate
import seol.study.secr.dto.FormLoginDto
import seol.study.secr.security.token.PreAuthorizationToken
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FormLoginFilter(defaultFilterProcessesUrl: String?) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {
//class FormLoginFilter : AbstractAuthenticationProcessingFilter {
//
//    lateinit var formLoginAuthenticationSuccessHandler: AuthenticationSuccessHandler
//    lateinit var formLoginAuthenticationFailureHandler: AuthenticationFailureHandler
//
//    constructor(defalutUrl: String, successHandler: AuthenticationSuccessHandler, failureHandler: AuthenticationFailureHandler) : super(defalutUrl) {
//        this.formLoginAuthenticationSuccessHandler = successHandler
//        this.formLoginAuthenticationFailureHandler = failureHandler
//    }
    val log by LoggerDelegate()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        // Request 데이터로 DTO 생성.
        val dto: FormLoginDto = ObjectMapper().readValue(request?.reader, FormLoginDto::class.java)

        // PreAuthorizationToken 생성하여 authenticate에 넘긴다.
        val token: PreAuthorizationToken = PreAuthorizationToken(dto.id, dto.password)
        return super.getAuthenticationManager().authenticate(token) // Provider 호출됨.
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
//        super.successfulAuthentication(request, response, chain, authResult)
        super.getSuccessHandler().onAuthenticationSuccess(request, response, authResult)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
//        super.unsuccessfulAuthentication(request, response, failed)
        log.error("인증 실패")
    }
}