package com.backend.ecommerce.application.service;

import com.backend.ecommerce.domain.model.User;
import com.backend.ecommerce.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User guardar(User user) {
        return userRepository.save(user);
    }
    public List<User> obtener_todos() {
        return userRepository.findAll();
    }

    public User buscarUsuario_ID(Integer id)throws ClassNotFoundException {
        Optional<User> userID = userRepository.findById(id);
        if(userID.isEmpty()) {
            throw new ClassNotFoundException("Usuario no encontrada");
        }
        return userID.get();
    }
    public void eliminar(Integer id) {
        userRepository.deleteById(id);
    }
    public User buscarPorEmail(String email)throws UsernameNotFoundException {
        Optional<User> userBBDD = userRepository.findUserByEmail(email);
        if(userBBDD.isEmpty()) {
            throw new UsernameNotFoundException("Usuario con email "+email+" no encontrado");
        }
        return userBBDD.get();
    }
}
