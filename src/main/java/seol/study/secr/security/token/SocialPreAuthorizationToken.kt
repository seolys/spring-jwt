package seol.study.secr.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import seol.study.secr.dto.SocialLoginDto

class SocialPreAuthorizationToken : UsernamePasswordAuthenticationToken {

    constructor(dto: SocialLoginDto) : super(dto.provider, dto)

    fun getDto() : SocialLoginDto = super.getCredentials() as SocialLoginDto


}