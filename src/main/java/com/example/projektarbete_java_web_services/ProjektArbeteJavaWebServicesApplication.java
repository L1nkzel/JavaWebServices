package com.example.projektarbete_java_web_services;

import com.example.projektarbete_java_web_services.entities.AppUser;
import com.example.projektarbete_java_web_services.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjektArbeteJavaWebServicesApplication implements CommandLineRunner {

    @Value("${token.secret}")
    private String secret;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepo.save(new AppUser("Dawud", passwordEncoder.encode("1234"), Role.ADMIN));
        userRepo.save(new AppUser("Shqiponja", passwordEncoder.encode("1234"), Role.USER));
        userRepo.findUsersByUsernameIgnoreCase("dawud").ifPresent(System.out::println);
        System.out.println(secret);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteJavaWebServicesApplication.class, args);


    }
}
