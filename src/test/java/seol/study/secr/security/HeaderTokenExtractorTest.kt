package seol.study.secr.security

import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Test

class HeaderTokenExtractorTest {
    @Test
    fun headerExtract() {
        val headerTokenExtractor = HeaderTokenExtractor()
        val header: String = "Bearer TOKEN_VALUE"

        assertThat(headerTokenExtractor.extract(header), Is.`is`("TOKEN_VALUE"));
    }
}