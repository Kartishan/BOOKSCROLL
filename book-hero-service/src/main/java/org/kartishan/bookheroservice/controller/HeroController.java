package org.kartishan.bookheroservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookheroservice.model.HeroDTO;
import org.kartishan.bookheroservice.request.HeroRequest;
import org.kartishan.bookheroservice.service.HeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/heroes")
@RequiredArgsConstructor
public class HeroController {

    private final HeroService heroService;

    @PostMapping("/add")
    public ResponseEntity<String> addHero(@RequestBody HeroRequest heroRequest) {
        try {
            heroService.addHero(heroRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Hero was successfully added.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
    }
    @GetMapping("/all/{bookId}")
    public ResponseEntity<List<HeroDTO>> getHeroesByBookId(@PathVariable UUID bookId) {
        List<HeroDTO> heroDTOList = heroService.findAllHeroesByBookId(bookId);
        return ResponseEntity.ok(heroDTOList);
    }

}
