package com.example.agency.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;

@Controller
public class HomeController {

    @GetMapping("/")
    public Mono<Void> index(ServerHttpResponse response) {
        // Redirige http://localhost:8080/ vers Swagger UI
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create("/webjars/swagger-ui/index.html"));
        return response.setComplete();
    }
}