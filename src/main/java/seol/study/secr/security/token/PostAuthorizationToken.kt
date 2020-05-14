package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import seol.study.secr.security.AccountContext

class PostAuthorizationToken(principal: Any?, credentials: Any?, authorities: MutableCollection<out GrantedAuthority>?)
    : UsernamePasswordAuthenticationToken(principal, credentials, authorities) {

    companion object {
        fun getTokenFromAccountContext(context: AccountContext) : PostAuthorizationToken {
            return PostAuthorizationToken(context, context.password, context.authorities)
        }
    }

}