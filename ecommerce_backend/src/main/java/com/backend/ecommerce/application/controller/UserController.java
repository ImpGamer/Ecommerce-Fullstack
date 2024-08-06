package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.UserService;
import com.backend.ecommerce.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3200")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<User> save_user(@RequestBody User user) {
        return new ResponseEntity<>(userService.guardar(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id)throws ClassNotFoundException {
        return ResponseEntity.ok(userService.buscarUsuario_ID(id));
    }
}
