package seol.study.secr.dto

import com.fasterxml.jackson.annotation.JsonProperty
import seol.study.secr.domain.SocialProvider

data class SocialLoginDto(
        @field:JsonProperty("provider")
        val provider: SocialProvider? = null,
        @field:JsonProperty("token")
        val token: String? = null
)