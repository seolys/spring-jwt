package seol.study.secr.security.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import seol.study.secr.domain.Account
import seol.study.secr.repository.AccountRepository
import seol.study.secr.security.AccountContext
import seol.study.secr.security.AccountContextService
import seol.study.secr.security.token.PostAuthorizationToken
import seol.study.secr.security.token.PreAuthorizationToken

@Component
open class FormLoginAuthenticationProvider : AuthenticationProvider {
    @Autowired
    lateinit var accountRepository: AccountRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(authentication: Authentication?): Authentication {
        // 인증 프로세스
        if (authentication !is PreAuthorizationToken) {
            throw ClassCastException("PreAuthorizationToken이 아닙니다")
        }
        val userId: String = authentication.getUserId()
        val userPassword: String = authentication.getUserPassword()

        // 아이디로 정보 조회.
        val findAccount: Account = accountRepository.findByUserId(userId).orElseThrow{ NoSuchElementException("아이디 조회에 실패하였습니다.") }

        // 패스워드 검증
        if (!isCorrectAccount(userPassword, findAccount)) {
            throw NoSuchElementException("인증정보가 정확하지 않습니다.")
        }

        return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(findAccount))
    }

    private fun isCorrectAccount(password: String, account: Account): Boolean {
//       return passwordEncoder.matches(account.password, password)
       return passwordEncoder.matches(password, account.password)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return PreAuthorizationToken::class.java.isAssignableFrom(authentication)
    }

}