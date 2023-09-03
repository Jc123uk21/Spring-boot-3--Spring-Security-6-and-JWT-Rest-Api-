package com.jcatchploe.FoodStore.Services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class JwtService {
	
	//Note the secret signing key should be kept in a secure  location accessible to the production software only!
		@Value("${jwt.secret}")
		private String SECRET_KEY;
		
		public String getUsername(@NotNull String token) {
			return getClaims(token).getSubject();
		}
		
		public<T> T extractClaims(@NotNull String token, Function<Claims, T> claimResolver) {
			final Claims claims = getClaims(token);
			return claimResolver.apply(claims);
		}

		public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
			return Jwts
					.builder()
					.setClaims(extraClaims)
					.setSubject(userDetails.getUsername())
					.claim("authorities", userDetails.getAuthorities())
					.setIssuer("FoodStore")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *12))
					.signWith(getSigningKey(), SignatureAlgorithm.HS256)
					.compact();
			
		}
		
		public String generateToken(@NotNull UserDetails userDetails) {
			return generateToken(new HashMap<>(), userDetails);
		}
		
		public boolean isTokenValid(String token, UserDetails userDetails) {
			final String username = getUsername(token);
			return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		}
		
		private boolean isTokenExpired(String token) {
			// TODO Auto-generated method stub
			return extractExpiration(token).before(new Date());
		}

		private Date extractExpiration(String token) {
			// TODO Auto-generated method stub
			return extractClaims(token, Claims::getExpiration);
		}

		private Claims getClaims(String token) {
			return Jwts
					.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		}

		private Key getSigningKey() {
			byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
			return Keys.hmacShaKeyFor(keyBytes);
		}


}
