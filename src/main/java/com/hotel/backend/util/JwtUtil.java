package com.hotel.backend.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 this class is used to generate the token
*/
@Component
public class JwtUtil {
	
	private String generateToken(Map<String, Object> extraClaims, UserDetails details) {
		return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
				.signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode("dXNlcg==");
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
