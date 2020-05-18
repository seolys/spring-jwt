package seol.study.secr.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seol.study.secr.domain.SocialProvider;
import seol.study.secr.security.filter.FormLoginFilter;
import seol.study.secr.security.filter.JwtAuthenticationFilter;
import seol.study.secr.security.filter.SocialLoginFilter;
import seol.study.secr.security.handler.LoginAuthenticationSuccessHandler;
import seol.study.secr.security.handler.JwtAuthenticationFailureHandler;
import seol.study.secr.security.provider.FormLoginAuthenticationProvider;
import seol.study.secr.security.provider.JwtAuthenticationProvider;
import seol.study.secr.security.provider.SocialLoginAuthenticationProvider;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginAuthenticationSuccessHandler successHandler;
    @Autowired
    private FormLoginAuthenticationProvider formProvider;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;
    @Autowired
    private JwtAuthenticationFailureHandler jwtFailureHandler;

    @Autowired
    private SocialLoginAuthenticationProvider socialProvider;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(this.formProvider)
            .authenticationProvider(this.socialProvider)
            .authenticationProvider(this.jwtProvider)
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 세션을 만들지 않도록 한다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/h2-console**").permitAll();

        // 필터 등록.
        http
            .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(socialFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/formlogin");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    protected JwtAuthenticationFilter jwtFilter() throws Exception {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("/formlogin", "/social"), "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, headerTokenExtractor);
        filter.setAuthenticationFailureHandler(jwtFailureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    protected SocialLoginFilter socialFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/social");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

}
