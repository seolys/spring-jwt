package seol.study.secr.security

import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import seol.study.secr.security.exception.InvalidJwtException

@Component
class HeaderTokenExtractor {
    val HEADER_PREFIX = "Bearer "

    fun extract(header: String?): String {
        if (StringUtils.isEmpty(header) || header!!.length < HEADER_PREFIX.length)
            throw InvalidJwtException("올바른 토큰 정보가 아닙니다.")
        return header.substring(HEADER_PREFIX.length)
    }

}