package com.backend.ecommerce.application.service;

import com.backend.ecommerce.domain.model.User;
import com.backend.ecommerce.domain.model.UserCredentials;
import com.backend.ecommerce.infraestructure.configuration.security.jwt.JWTUtilityService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JWTUtilityService jwtUtilityService;

    public ResponseEntity<?> register(User user) {
        if(!userApproved(user)) {
            return new ResponseEntity<>("El correo '"+user.getEmail()+"' ya se encuentra registrado",null,HttpStatus.IM_USED);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Password encriptada: "+user.getPassword());
        return ResponseEntity.ok(userService.guardar(user));
    }

    public ResponseEntity<String> login(UserCredentials credentials) {
        Map<String,String> jwt = new HashMap<>();
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.buscarPorEmail(credentials.email());

            jwt.put("id",user.getId().toString());
            jwt.put("token",jwtUtilityService.generateToken(credentials.email()));
            jwt.put("type",user.getUserType().toString());
            Gson gson = new Gson();

            return ResponseEntity.ok(gson.toJson(jwt));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            jwt.put("Message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    private boolean userApproved(User user) {
        try {
            userService.buscarPorEmail(user.getEmail());
            return false;
        }catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
            return true;
        }
    }
}
