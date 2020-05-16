package seol.study.secr.security

import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import kotlin.streams.toList

class FilterSkipMatcher: RequestMatcher {

    private val orRequestMatcher: OrRequestMatcher
    private val processingMatcher: RequestMatcher

    constructor(pathToSkip: List<String>, processingPath: String) {
        this.orRequestMatcher = OrRequestMatcher(pathToSkip.stream().map { AntPathRequestMatcher(it) }.toList())
        this.processingMatcher = AntPathRequestMatcher(processingPath)
    }

    override fun matches(req: HttpServletRequest?): Boolean {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req)
    }
}