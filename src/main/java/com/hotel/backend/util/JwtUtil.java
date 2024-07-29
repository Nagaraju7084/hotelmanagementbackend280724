package com.hotel.backend.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 this class is used to generate the token
*/
@Component
public class JwtUtil {
	
	//generate the jwt token
	//parameters claims and user details
	private String generateToken(Map<String, Object> extraClaims, UserDetails details) {
		return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
				.signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
	}
	
	//the above generate token method is private, so we have to call using login api
	public String generateToke(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	//check the validity of the token
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	//parsing and validating jwt
	//parse the token and get the data from jwt and to extract all the claims from jwt
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
	}
	
	//get the username from the token by calling extractClaim method
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//to extract the expiration
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	//check is token expired or not
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); //if is expiration date is before the current date and time then the method will return true, idicating that token is expired 
	}
	
	//return the specified claim by calling extractAllClaims method
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode("dXNlcg==");
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
