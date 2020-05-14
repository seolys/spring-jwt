package seol.study.secr.dto

import com.fasterxml.jackson.annotation.JsonProperty

class FormLoginDto(
        @field:JsonProperty("userid")
        val id: String? = null,

        @field:JsonProperty("password")
        val password: String? = null
)