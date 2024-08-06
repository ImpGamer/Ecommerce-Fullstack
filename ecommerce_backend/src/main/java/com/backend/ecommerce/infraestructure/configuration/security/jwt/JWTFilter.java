package com.backend.ecommerce.infraestructure.configuration.security.jwt;

import com.backend.ecommerce.application.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import static com.backend.ecommerce.infraestructure.configuration.security.jwt.JWTUtilityService.AUTHORIZATION_HEADER;

@Service
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtilityService jwtutilityService;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if(header == null) {
            filterChain.doFilter(request,response);
            return;
        }
        log.info(header);
        try {
            Claims claims = jwtutilityService.getClaims(header);
            if(claims.get("authorities") != null) {
                jwtutilityService.setAuthentication(claims,customUserDetailService);
            } else {
                //Limpiar contexto y sacar de sesion al usuario
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request,response);
        }catch (Exception ex) {
            log.error("Error del JWT");
            log.error(ex.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

}
