package com.example.projektarbete_java_web_services.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public LoginService(JwtUtils jwtUtils, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> autenticate(String username, String id, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {

            return ResponseEntity.ok(jwtUtils.generateToken(username, id));
        } else {
            return ResponseEntity.status(401).body("Username or password is incorrect, please try again");
        }
    }

    public Boolean validate(String token) {
        return jwtUtils.validate(token);
    }
}
