package seol.study.secr.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import seol.study.secr.dto.TokenDto
import seol.study.secr.security.AccountContext
import seol.study.secr.security.JwtFactory
import seol.study.secr.security.token.PostAuthorizationToken
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class FormLoginAuthenticationSuccessHandler : AuthenticationSuccessHandler{

    @Autowired
    lateinit var jwtFactory: JwtFactory
    @Autowired
    lateinit var objectMapper: ObjectMapper

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, auth: Authentication?) {
        val context = auth!!.principal as AccountContext
        val token: String = jwtFactory.generateToken(context)
        val tokenDto = TokenDto(token = token)
        processResponse(response, tokenDto)
    }

    private fun processResponse(response: HttpServletResponse?, tokenDto: TokenDto) {
        val res = response!!
        res.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        res.status = HttpStatus.OK.value()
        res.writer.write(objectMapper.writeValueAsString(tokenDto))
    }

}