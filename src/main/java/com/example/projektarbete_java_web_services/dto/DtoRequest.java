package com.example.projektarbete_java_web_services.dto;

import com.example.projektarbete_java_web_services.Role;

public record DtoRequest(String username, String password, Role role) {
}
