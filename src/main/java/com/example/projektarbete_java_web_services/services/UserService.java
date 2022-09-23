package com.example.projektarbete_java_web_services.services;


import com.example.projektarbete_java_web_services.dto.DtoRequest;
import com.example.projektarbete_java_web_services.dto.DtoResponse;
import com.example.projektarbete_java_web_services.dto.Post;
import com.example.projektarbete_java_web_services.entities.AppUser;
import com.example.projektarbete_java_web_services.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final WebClient webClient;

    public UserService(UserRepo userRepo, WebClient webClient) {
        this.userRepo = userRepo;
        this.webClient = webClient;
    }

    public List<DtoResponse> findAll(String contains) {
        return userRepo
                .findUsersByUsernameContaining(contains)
                .stream()
                .map(user -> new DtoResponse(user.getUserId(), user.getUsername()))
                .toList();
    }

    public DtoResponse insertAppUser(DtoRequest dtoRequest) {


        AppUser existingUser = userRepo.save(new AppUser(dtoRequest.username(), dtoRequest.password(), dtoRequest.role()));

        return new DtoResponse(existingUser.getUserId(), existingUser.getUsername());
    }

    public DtoResponse findById(int id) {
        AppUser existingUser = userRepo.findById(id).orElseThrow();

        return new DtoResponse(existingUser.getUserId(), existingUser.getUsername());
    }

    public int findIdByUsername(Authentication authentication) {
        int id = userRepo.findByUsername(authentication.getName()).getUserId();
        return id;
    }

    public String findRoleByCurrentUser(Authentication authentication) {
        String role = userRepo.findByUsername(authentication.getName()).getRole().toString();
        return role;
    }

    public DtoResponse updateById(int id, DtoRequest dtoRequest) {

        AppUser existingUser = userRepo
                .findById(id)
                .orElseThrow();

        if (dtoRequest.username() != null) {
            existingUser.setUsername(dtoRequest.username());
        }

        AppUser createdUser = userRepo.save(existingUser);
        return new DtoResponse(createdUser.getUserId(), createdUser.getUsername());
    }

    public void deleteById(int id) {
        userRepo.deleteById(id);
    }


    public List<Post> findPostsByUserId(int id) {

        return webClient
                .get()
                .uri("/posts?userId=" + id)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Post.class))
                .buffer()
                .blockLast();
    }

}





