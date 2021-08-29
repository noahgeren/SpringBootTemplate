package tech.noahgeren.template.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.noahgeren.template.domain.User;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final int EXPIRATION_TIME = 864_000; // 10 days
	
	private final AuthenticationManager authenticationManager;
	private final byte[] secretKey;
	
	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String secretKey) {
		this.authenticationManager = authenticationManager;
		this.secretKey = secretKey.getBytes();
	}

	@Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
            		new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) 
    		throws IOException, ServletException {
		final User user = (User) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000l))
                .sign(Algorithm.HMAC512(secretKey));
        final Cookie authCookie = buildCookie(token, EXPIRATION_TIME);
        res.addCookie(authCookie);
        final String userJson = objectMapper.writeValueAsString(user);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        final PrintWriter out = res.getWriter();
        out.print(userJson);
        out.flush();
    }
	
	public void logout(HttpServletResponse res) {
		final Cookie logoutCookie = buildCookie(null, 0);
		res.addCookie(logoutCookie);
	}
	
	private Cookie buildCookie(String token, int expiration) {
		final Cookie cookie = new Cookie("jwt-token", token);
		cookie.setMaxAge(expiration);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
        return cookie;
	}
	
}
