package seol.study.secr.security.exception

import java.lang.RuntimeException

class InvalidJwtException(msg: String): RuntimeException(msg) {
}