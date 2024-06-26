package com.example.peer.security.utils;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.peer.security.entity.TokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;

	//    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
	public JwtTokenProvider(@Value("${spring.security.jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public TokenInfo generateToken(UserDetails user) {
		String authorities = user.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		log.debug(">> creating token with username {}", user.getUsername());
		String accessToken = Jwts.builder()
			.setSubject(user.getUsername())
			.claim("auth", authorities)
			.setExpiration(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(8))))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		String refreshToken = Jwts.builder()
			.setExpiration(Date.from(LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.ofHours(8))))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
		// refreshTokenRepository.save(new RefreshToken(String.valueOf(user.getUsername()), refreshToken, accessToken));

		return TokenInfo.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	// JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
	public Authentication getAuthentication(String accessToken) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();

		if (claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	//토큰 정보를 검증하는 메서드
	public String validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return "Valid";
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
			return "Invalid";
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			return "Expired";
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			return "Unsupported";
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
			return "Empty";
		}
	}

	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public String resolveToken(String authorization) {
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
			log.debug(">> resolved token by JwtTokenProvider");

			return authorization.substring(7);
		}
		return null;
	}
}
