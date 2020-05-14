package seol.study.secr.repository

import org.springframework.data.repository.CrudRepository
import seol.study.secr.domain.Account
import java.util.*

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByUserId(userId: String) : Optional<Account>

    fun findBySocialId(socialId: Long) : Optional<Account>
}