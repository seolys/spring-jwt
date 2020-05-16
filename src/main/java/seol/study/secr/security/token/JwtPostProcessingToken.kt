package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import seol.study.secr.domain.UserRole

class JwtPostProcessingToken: UsernamePasswordAuthenticationToken {

    constructor(username: String, role: UserRole): super(
            username,
            null,
            mutableListOf(SimpleGrantedAuthority(role.roleName))
    )

    fun getUserId(): String {
        return super.getPrincipal() as String
    }

    fun getPassword(): String {
        return super.getCredentials() as String
    }

}