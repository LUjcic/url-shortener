package hr.lujcic.urlshortener.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hr.lujcic.urlshortener.exceptions.NoAuthorizationTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authorization filter used in checking the JWT authorization token in header
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${secret.key}")
    String secretKey = "MegaUltraSuperRareNeverFoundNeverDiscoveredSecretKey";

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * Validates given JWT token and generates authentication token if JWT token is valid
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            chain.doFilter(request, response);
            return;
        }
        String[] splitToken = authorization.split(" ");
        DecodedJWT verified = JWT.require(Algorithm.HMAC512(secretKey))
            .build()
            .verify(splitToken[1]);

        String authoritiesString = verified.getClaim("authorities").asString();
        Set<GrantedAuthority> authorities = Arrays.stream(authoritiesString.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                new User(verified.getSubject(), "", authorities),
                null,
                authorities
            )
        );
        chain.doFilter(request, response);
    }
}
