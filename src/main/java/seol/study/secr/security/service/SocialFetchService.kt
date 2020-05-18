package seol.study.secr.security.service

import seol.study.secr.dto.SocialLoginDto
import seol.study.secr.security.social.SocialUserProperty

interface SocialFetchService {
    fun getSocialUserInfo(dto: SocialLoginDto) : SocialUserProperty
}