package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class JwtPreProcessingToken: UsernamePasswordAuthenticationToken {
    constructor(token: String?): super(token, null)
}