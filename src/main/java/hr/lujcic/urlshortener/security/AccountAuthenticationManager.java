package hr.lujcic.urlshortener.security;

import hr.lujcic.urlshortener.exceptions.AccountNotFoundException;
import hr.lujcic.urlshortener.model.Account;
import hr.lujcic.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of authentication manager used in authenticating users on /login, uses account service and password encoder
 */
public class AccountAuthenticationManager implements AuthenticationManager {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountAuthenticationManager(AccountService accountService, BCryptPasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param authentication contains username and password
     * @return approved authentication token with username and authorities
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Account account = null;
        try {
            account = accountService.getByUsername(username);
        } catch (AccountNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException(username);
        }
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("USER");
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(userAuthority);
        if (passwordEncoder.matches(password, account.getPassword())) {
            return new UsernamePasswordAuthenticationToken(new User(username, "", authorities), account.getPassword(), authorities);
        } else throw new BadCredentialsException("Failed to login, username or password are wrong!");
    }
}
