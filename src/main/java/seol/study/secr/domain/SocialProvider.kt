package seol.study.secr.domain

import seol.study.secr.security.social.KakaoUserProperty
import seol.study.secr.security.social.SocialUserProperty

enum class SocialProvider(val userinfoEndpoint: String, val propertyMetaclass: Class<out SocialUserProperty>) {
    KAKAO("https://kapi.kakao.com/v2/user/me", KakaoUserProperty::class.java)
    ;

    fun getProviderName() : String {
        return this.name
    }

}