package hr.lujcic.urlshortener.security;

import hr.lujcic.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration of authorization and authentication of the application
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(AccountService accountService, BCryptPasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/account", "/short/**", "/help");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .formLogin().loginProcessingUrl("/auth").permitAll()
            .and()
            .authorizeHttpRequests().antMatchers("/statistic/**", "/register").authenticated()
            .and()
            .addFilter(new AuthenticationFilter(authenticationManager()))
            .addFilter(new AuthorizationFilter(authenticationManager()));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AccountAuthenticationManager(accountService, passwordEncoder);
    }
}
