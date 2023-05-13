package com.example.universityscheduler.config;

import com.example.universityscheduler.config.property.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
  private static final String AUTHORITIES_KEY = "auth";
  private final Key key;
  private final JwtParser jwtParser;
  private final long tokenValidityInMilliseconds;

  public JwtTokenProvider(JwtProperties jwtProperties) {
    byte[] keyBytes;
    String secret = jwtProperties.getBase64Secret();
    if (!ObjectUtils.isEmpty(secret)) {
      log.debug("Using a Base64-encoded JWT secret key");
      keyBytes = Decoders.BASE64.decode(secret);
    } else {
      log.warn("Warning: the JWT key used is not Base64-encoded.");
      secret = jwtProperties.getSecret();
      keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    }
    key = Keys.hmacShaKeyFor(keyBytes);
    jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    tokenValidityInMilliseconds = 1000 * jwtProperties.getTokenValidityInSeconds();
  }

  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    long now = new Date().getTime();
    Date validity = new Date(now + tokenValidityInMilliseconds);

    return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = jwtParser.parseClaimsJws(token).getBody();

    List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String authToken) {
    try {
      jwtParser.parseClaimsJws(authToken);
      return true;
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
      log.trace("Invalid JWT token", e);
    } catch (IllegalArgumentException e) {
      log.error("Token validation error {}", e.getMessage());
    }
    return false;
  }
}
