package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class PreAuthorizationToken(principal: Any?, credentials: Any?)
    : UsernamePasswordAuthenticationToken(principal, credentials) {

    fun getUserId() : String = super.getPrincipal() as String

    fun getUserPassword() : String = super.getCredentials() as String
}