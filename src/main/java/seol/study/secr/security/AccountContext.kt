package seol.study.secr.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import seol.study.secr.domain.Account
import seol.study.secr.domain.UserRole

class AccountContext : User {

    private constructor(username: String?, password: String?, authorities: MutableCollection<out GrantedAuthority>?) : super(username, password, authorities)

    companion object {
        fun fromAccountModel(account: Account) : AccountContext = AccountContext(account.userId, account.password, parseAuthorities(account.userRole))

        fun parseAuthorities(role: UserRole?) : MutableCollection<out GrantedAuthority>? = mutableListOf(SimpleGrantedAuthority(role!!.roleName))
    }
}