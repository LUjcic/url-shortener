package hr.lujcic.urlshortener.security;

import static java.lang.System.currentTimeMillis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authentication filter used in authenticating user and generating a JWT token
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Value("${token.expiration}")
    private long expiration = 24 * 60 * 60 * 1000L;
    @Value("${secret.key}")
    String secretKey = "MegaUltraSuperRareNeverFoundNeverDiscoveredSecretKey";

    private final AuthenticationManager authManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
    }

    /**
     * Grabs username and password from header and sends them to authentication manager
     * @param request
     * @param response
     * @return valid authentication token
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        return authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                username,
                password
            )
        );
    }

    /**
     * Generates JWT token on successful authentication and sets it in response header under Authorization
     * @param request
     * @param response
     * @param chain
     * @param authentication
     */
    @Override
    public void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authentication
    ) {
        String username = ((User) authentication.getPrincipal()).getUsername();

        String token = JWT.create().withSubject(username)
            .withClaim("authorities", "USER")
            .withExpiresAt(new Date(currentTimeMillis() + expiration))
            .sign(Algorithm.HMAC512(secretKey));

        response.setHeader("Authorization", "Bearer " + token);
    }
}
