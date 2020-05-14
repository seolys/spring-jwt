package seol.study.secr.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import seol.study.secr.security.filter.FormLoginFilter
import seol.study.secr.security.handler.FormLoginAuthenticationSuccessHandler
import seol.study.secr.security.provider.FormLoginAuthenticationProvider

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var formLoginAuthenticationSuccessHandler: FormLoginAuthenticationSuccessHandler

    @Autowired
    lateinit var provider: FormLoginAuthenticationProvider

    @Bean
    open fun getPasswordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    open fun objectMapper() : ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    open fun getAuthenticationManager() : AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(this.provider)
    }

    open fun formLoginFilter() : FormLoginFilter {
        val filter = FormLoginFilter("/formlogin")
        filter.setAuthenticationSuccessHandler(formLoginAuthenticationSuccessHandler)
        filter.setAuthenticationManager(super.authenticationManagerBean())
        return filter
    }

}