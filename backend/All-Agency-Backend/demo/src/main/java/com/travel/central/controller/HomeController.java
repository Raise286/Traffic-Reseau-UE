package com.travel.central.controller;
import com.travel.central.domain.dto.ReservationEventDto;
import com.travel.central.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Bienvenue sur mon application!";
    }
}
