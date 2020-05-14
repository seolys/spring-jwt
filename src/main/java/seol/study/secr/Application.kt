package seol.study.secr

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import seol.study.secr.domain.Account
import seol.study.secr.domain.UserRole
import seol.study.secr.repository.AccountRepository

@SpringBootApplication
open class Application {
    @Bean
    open fun bootstrapTestAccount(accountRepository: AccountRepository, encoder: PasswordEncoder) : CommandLineRunner {
        return CommandLineRunner {
            val password = encoder.encode("1234")
            val account = Account(userId = "seolnavy", password = password, userRole = UserRole.USER)
            accountRepository.save(account)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

