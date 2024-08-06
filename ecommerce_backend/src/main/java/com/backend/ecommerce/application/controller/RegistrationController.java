package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.RegistrationService;
import com.backend.ecommerce.domain.model.User;
import com.backend.ecommerce.domain.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/security/user")
@CrossOrigin(origins = "http://localhost:3200")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return registrationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        return registrationService.login(credentials);
    }

}
