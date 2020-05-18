package seol.study.secr.security.social

interface SocialUserProperty {
    fun getUserId() : String
    fun getUserNickname() : String
    fun getProfileHref() : String
    fun getEmail() : String

}