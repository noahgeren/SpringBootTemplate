package tech.noahgeren.template.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import tech.noahgeren.template.domain.User;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final byte[] secretKey;
	
	private final UserDetailsService userService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userService, String secretKey) {
		super(authenticationManager);
		this.userService = userService;
		this.secretKey = secretKey.getBytes();
	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
    		throws IOException, ServletException {
        String token = Arrays.stream(req.getCookies()).filter(c -> "jwt-token".equals(c.getName())).map(Cookie::getValue).findFirst().orElse(null);

        if (token == null) {
        	res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    	try {
            final String username = JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token)
                    .getSubject();

            if (username != null) {
            	try {
	            	final User user = (User) userService.loadUserByUsername(username);
	                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            	} catch (UsernameNotFoundException ex) {
            		return null;
            	}
            }
            return null;
    	} catch (TokenExpiredException e) {
    		return null;
    	}
    }

}
