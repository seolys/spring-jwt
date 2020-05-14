package seol.study.secr.security

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import seol.study.secr.LoggerDelegate
import seol.study.secr.domain.Account
import seol.study.secr.domain.UserRole

@SpringBootTest
@RunWith(SpringRunner::class)
open class JwtFactoryTest {
    val logger by LoggerDelegate()

    lateinit var context: AccountContext

    @Autowired
    lateinit var jwtFactory: JwtFactory

    @Before
    fun setUp() {
        val account: Account = Account(userId = "seolnavy", password = "1234", userRole = UserRole.USER)
        this.context = AccountContext.fromAccountModel(account)
    }

    @Test
    fun JWT_generate() {
        logger.info("JWT : " + jwtFactory.generateToken(this.context))
    }
}