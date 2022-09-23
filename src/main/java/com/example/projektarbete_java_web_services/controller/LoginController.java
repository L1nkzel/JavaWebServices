package com.example.projektarbete_java_web_services.controller;

import com.example.projektarbete_java_web_services.dto.JwtRequestDTO;
import com.example.projektarbete_java_web_services.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody JwtRequestDTO jwtRequestDTO) {
        return loginService.autenticate(jwtRequestDTO.username(), jwtRequestDTO.id(), jwtRequestDTO.password());
    }

    @GetMapping("/validate")
    public Boolean validate(@RequestParam String token) {
        return loginService.validate(token);
    }

}
