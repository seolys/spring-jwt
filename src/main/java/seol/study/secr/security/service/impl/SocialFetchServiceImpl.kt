package seol.study.secr.security.service.impl

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import seol.study.secr.domain.SocialProvider
import seol.study.secr.dto.SocialLoginDto
import seol.study.secr.security.service.SocialFetchService
import seol.study.secr.security.social.SocialUserProperty

@Service
open class SocialFetchServiceImpl : SocialFetchService {
    val HEADER_PREFIX: String = "Bearer "

    override fun getSocialUserInfo(dto: SocialLoginDto): SocialUserProperty {
        val provider = dto.provider
        val restTemplate = RestTemplate()

        val entity = HttpEntity("null", generateHeader(dto.token!!))

        return restTemplate.exchange(provider!!.userinfoEndpoint, HttpMethod.GET, entity, provider.propertyMetaclass).body
    }

    private fun generateHeader(token: String) : HttpHeaders {
        val headers: HttpHeaders = HttpHeaders();
        headers.add("Authorization", generateHeaderContent(token))
        return headers
    }

    private fun generateHeaderContent(token: String) : String = HEADER_PREFIX + token
}