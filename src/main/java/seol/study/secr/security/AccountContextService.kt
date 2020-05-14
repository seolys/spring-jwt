package seol.study.secr.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import seol.study.secr.domain.Account
import seol.study.secr.repository.AccountRepository
import javax.persistence.Id

class AccountContextService : UserDetailsService {
    @Autowired
    lateinit var accountRepository: AccountRepository

    override fun loadUserByUsername(userId: String?): UserDetails {
        if (userId == null)
            throw IllegalArgumentException("userId가 없습니다.")

        val account = accountRepository.findByUserId(userId).orElseThrow { NoSuchElementException("아이디에 맞는 계정이 없습니다") }
        return getAccountContext(account)
    }

    private fun getAccountContext(account: Account): AccountContext {
        return AccountContext.fromAccountModel(account)
    }

}