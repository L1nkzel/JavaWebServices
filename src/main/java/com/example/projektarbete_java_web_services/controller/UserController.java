package com.example.projektarbete_java_web_services.controller;

import com.example.projektarbete_java_web_services.dto.DtoRequest;
import com.example.projektarbete_java_web_services.dto.DtoResponse;
import com.example.projektarbete_java_web_services.dto.Post;

import com.example.projektarbete_java_web_services.services.PostService;
import com.example.projektarbete_java_web_services.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "JwtAuth")

public class UserController {

    private final UserService userService;
    private final PostService postService;


    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;

    }

    @GetMapping
    public List<DtoResponse> findAll(@RequestParam(required = false, defaultValue = "")
                                             String usrCont) {
        return userService.findAll(usrCont);
    }

    @GetMapping("/{id}")

    public ResponseEntity<DtoResponse> findById(@PathVariable int id, Authentication authentication) {
        if (userService.findIdByUsername(authentication) == id || userService.findRoleByCurrentUser(authentication).equals("ADMIN")) {
            System.out.println(authentication);
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } else {
            System.out.println("fungerar inte");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findAllByUserId(@PathVariable("id") int id, Authentication authentication) {
        if (userService.findIdByUsername(authentication) == id || userService.findRoleByCurrentUser(authentication).equals("ADMIN")) {
            System.out.println(authentication);
            List<Post> posts = userService.findPostsByUserId(id);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            System.out.println("You are not an admin");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping
    public DtoResponse insertAppUser(@RequestBody DtoRequest dtoRequest) {
        return userService.insertAppUser(dtoRequest);
    }

    @PutMapping("/{id}")
    public DtoResponse updateById(
            @PathVariable int id,
            @RequestBody DtoRequest dtoRequest) {
        return userService.updateById(id, dtoRequest);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "deleted user";
    }


}