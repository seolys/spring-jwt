package seol.study.secr.security.service.impl

import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import seol.study.secr.LoggerDelegate
import seol.study.secr.domain.SocialProvider
import seol.study.secr.dto.SocialLoginDto
import seol.study.secr.security.social.SocialUserProperty

class SocialFetchServiceImplTest {
    val log by LoggerDelegate()

    val service = SocialFetchServiceImpl()
    lateinit var dto: SocialLoginDto

    @Before
    fun setUp() {
        this.dto = SocialLoginDto(SocialProvider.KAKAO, "UrAXw0azv6n2fRzIJ27juRIFK-4ooZg4mxlzlwo9dNoAAAFyIOP3xA")
    }

    @Test
    fun restTemplate_practice1() {
        log.debug(RestTemplate().getForObject("https://www.naver.com"))
    }

    @Test
    fun service_fetchSocialInfo() {
        var property = service.getSocialUserInfo(this.dto)
        println(property.getEmail())
        assertThat(property.getEmail(), Is.`is`("outsiderys@naver.com"))
    }

}