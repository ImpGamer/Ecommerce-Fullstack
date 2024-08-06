package com.backend.ecommerce.infraestructure.configuration.security.jwt;

import com.backend.ecommerce.application.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JWTUtilityService {
    static final String AUTHORIZATION_HEADER = "Authorization";
    private final String SECRET_KEY = "CZ44DJytmP9LgWuwfKffoQVM2kbVr6KeuWtg4ri5VJNHFOZB5GPg91KriXR5YyUsYtbSvf";
    private final long EXPIRATION_TIME = 3600000;

    public String generateToken(String email) {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().toString()
        );

        String token = "";
        try {
            token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                    .claim("authorities",authorities.stream().map(GrantedAuthority::getAuthority).toList())
                    .signWith(getSignedKey(SECRET_KEY), SignatureAlgorithm.HS512).compact();

        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return token;
    }
    private Key getSignedKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey(SECRET_KEY)).build()
                .parseClaimsJws(token).getBody();
    }
    public void setAuthentication(Claims claims, CustomUserDetailService userDetailService) {
        UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
