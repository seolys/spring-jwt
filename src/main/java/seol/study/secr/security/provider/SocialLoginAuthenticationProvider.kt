package seol.study.secr.security.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import seol.study.secr.LoggerDelegate
import seol.study.secr.domain.Account
import seol.study.secr.domain.UserRole
import seol.study.secr.dto.SocialLoginDto
import seol.study.secr.repository.AccountRepository
import seol.study.secr.security.AccountContext
import seol.study.secr.security.service.SocialFetchService
import seol.study.secr.security.social.SocialUserProperty
import seol.study.secr.security.token.PostAuthorizationToken
import seol.study.secr.security.token.SocialPreAuthorizationToken

@Component
open class SocialLoginAuthenticationProvider : AuthenticationProvider {
    val log by LoggerDelegate()

    @Autowired
    lateinit var accountRepository: AccountRepository
    @Autowired
    lateinit var service: SocialFetchService

    override fun supports(authentication: Class<*>?): Boolean {
        return SocialPreAuthorizationToken::class.java.isAssignableFrom(authentication)
    }

    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication as SocialPreAuthorizationToken
        val dto = token.getDto()
        val account = getAccount(dto)
        return PostAuthorizationToken.getTokenFromAccountContext(AccountContext(account))
    }

    private fun getAccount(dto: SocialLoginDto): Account {
        val property = service.getSocialUserInfo(dto);
        val userId = property.getUserId()
        val provider = dto.provider

        val account: Account = accountRepository.findBySocialIdAndSocialProvider(userId.toLong(), provider!!)
                                            .orElseGet {
                                                Account(
                                                        username = property.getUserNickname(),
                                                        userId = property.getEmail(),  // UserId고민필요.. 현재 임시처리.
                                                        password = "SOCIAL_LOGIN",
                                                        userRole = UserRole.USER,
                                                        socialId = property.getUserId().toLong(),
                                                        socialProvider = provider,
                                                        profilePicture = property.getProfileHref()
                                                )
                                            }
        log.info("account.id : ${account.id}")
        log.info("property.getUserNickname() : ${property.getUserNickname()}")
        return account;
    }

}