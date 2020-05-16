package seol.study.secr.security.filter

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import seol.study.secr.LoggerDelegate
import seol.study.secr.security.HeaderTokenExtractor
import seol.study.secr.security.token.JwtPreProcessingToken
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter : AbstractAuthenticationProcessingFilter {
    val log by LoggerDelegate()
    val headerTokenExtractor: HeaderTokenExtractor

    constructor(matcher: RequestMatcher, headerTokenExtractor: HeaderTokenExtractor) : super(matcher) {
        this.headerTokenExtractor = headerTokenExtractor
    }

    override fun attemptAuthentication(req: HttpServletRequest?, res: HttpServletResponse?): Authentication {
        val tokenPayload = req?.getHeader("Authorization")
        val token = JwtPreProcessingToken(headerTokenExtractor.extract(tokenPayload))
        return super.getAuthenticationManager().authenticate(token)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain, authResult: Authentication?) {
//        super.successfulAuthentication(request, response, chain, authResult)
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authResult
        SecurityContextHolder.setContext(context)
        chain.doFilter(request, response)
    }

//    override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
//        super.unsuccessfulAuthentication(request, response, failed)
////        super.getFailureHandler().onAuthenticationFailure(request, response, failed)
//    }

}