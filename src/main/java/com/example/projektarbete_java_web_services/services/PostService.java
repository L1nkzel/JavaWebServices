package com.example.projektarbete_java_web_services.services;

import com.example.projektarbete_java_web_services.dto.DtoResponse;
import com.example.projektarbete_java_web_services.dto.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PostService {

    private final WebClient webClient;

    public PostService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Post> findAll() {
        return webClient
                .get()
                .uri("/posts")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Post.class))
                .buffer()
                .blockLast();

    }

    public Post insertPost(Post post) {
        return webClient
                .post()
                .uri("/posts")
                .body(Mono.just(post), Post.class)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Post.class))
                .block();
    }

//    public Post deletePost(String id) {
//        return webClient
//                .delete()
//
//    }


}
