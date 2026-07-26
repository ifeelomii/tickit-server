package com.example.tickit.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.tickit.domains.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getUserRoles().name());
		claims.put("email", user.getEmail());

		return generateToken(claims, String.valueOf(user.getId()));
	}

	public String generateToken(Map<String, Object> extraClaims, String username) {

		return Jwts.builder().claims(extraClaims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(getSigningKey()).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		return extractAllClaims(token).get("role", String.class);
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).get("email", String.class);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	public boolean isTokenValid(String token, User user) {

		String username = extractUsername(token);

		return username.equals(user.getId().toString()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	private SecretKey getSigningKey() {

		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}
