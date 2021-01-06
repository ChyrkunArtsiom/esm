package com.epam.esm.util.jwt;

import com.epam.esm.dto.AuthenticationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The class-implementation of {@link JwtUtil}.
 */
@Service
public class JwtUtilImpl implements JwtUtil {

    private static final String CLAIM_KEY_USER_ID = "userId";

    public JwtUtilImpl() {
    }

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private String EXPIRATION_DAYS;

    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        Integer userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Integer.parseInt(claims.get(CLAIM_KEY_USER_ID).toString());
        } catch (Exception ex) {
            userId = null;
        }
        return userId;
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        AuthenticationUser user = (AuthenticationUser)userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, user.getId());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * Integer.valueOf(EXPIRATION_DAYS)))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
