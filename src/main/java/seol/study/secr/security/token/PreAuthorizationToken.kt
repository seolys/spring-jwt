package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import seol.study.secr.dto.SocialLoginDto

class PreAuthorizationToken : UsernamePasswordAuthenticationToken {

    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)

    fun getUserId() : String = super.getPrincipal() as String

    fun getUserPassword() : String = super.getCredentials() as String
}