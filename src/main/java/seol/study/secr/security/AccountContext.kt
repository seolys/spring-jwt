package seol.study.secr.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import seol.study.secr.domain.Account
import seol.study.secr.domain.UserRole
import seol.study.secr.security.token.JwtPostProcessingToken


class AccountContext : User {

    private lateinit var account : Account

    constructor(account: Account) : super(account.userId, account.password, parseAuthorities(account.userRole)) {
        this.account = account
    }

    constructor(username: String, password: String, roleName: Collection<GrantedAuthority>) : super(username, password, roleName)

    constructor(username: String?, password: String?, RoleName: String) : super(username, password, parseAuthorities(UserRole.getRoleByName(RoleName)))

    companion object {
        fun fromAccountModel(account: Account) : AccountContext = AccountContext(account)

        fun fromJwtPostToken(token: JwtPostProcessingToken): AccountContext =
                AccountContext(token.getUserId(), token.getPassword(), token.authorities)

        fun parseAuthorities(role: UserRole?) : MutableCollection<out GrantedAuthority>? = mutableListOf(SimpleGrantedAuthority(role?.roleName))
    }

    fun getAccount(): Account? {
        return account
    }

}

