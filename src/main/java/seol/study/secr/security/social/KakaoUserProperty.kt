package seol.study.secr.security.social

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserProperty(
        @field:JsonProperty("id")
        val userUniqueId: Long? = null,
        @field:JsonProperty("properties")
        val userProperties: MutableMap<String, Any?>? = null,
        @field:JsonProperty("kakao_account")
        val kakaoAccount: MutableMap<String, Any?>? = null
) : SocialUserProperty {

    override fun getUserId(): String {
        return userUniqueId.toString()
    }

    override fun getUserNickname(): String {
        val profile = kakaoAccount?.get("profile") as MutableMap<String, String>
        if (profile == null || profile["nickname"] == null)
            return ""
        else
            return profile["nickname"].toString()
    }

    override fun getProfileHref(): String {
        val profile = kakaoAccount?.get("profile") as MutableMap<String, String>
        if (profile == null || profile["nickname"] == null)
            return ""
        else
            return profile["profile_image_url"].toString()
    }

    override fun getEmail(): String {
        val email = kakaoAccount?.get("email") as String
        return if (email == null) "" else email
    }
}