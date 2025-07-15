//business logic

package com.notes.notesapp.service;


import com.notes.notesapp.dto.*;
import com.notes.notesapp.entity.User;
import com.notes.notesapp.repository.UserRepository;
import com.notes.notesapp.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepo.save(user);
        return "User registered successfully!";
    }

    public String login(AuthRequest request) {
        // Authenticate user (will throw if invalid)
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Generate token if valid
        return jwtUtil.generateToken(request.getUsername());
    }
}
