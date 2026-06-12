package com.example.day5.controller;

import com.example.day5.dto.CardCreateRequest;
import com.example.day5.dto.CardResponse;
import com.example.day5.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public CardResponse create(@RequestBody @Valid CardCreateRequest request) {
        return cardService.create(request);
    }

    @PutMapping("/deductAll/{id}")
    public ResponseEntity<Void> deductAll(@PathVariable Long id) {
        cardService.deductAll(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public CardResponse checkMasking(@PathVariable Long id) {
        CardResponse cardResponse = cardService.find(id);
        return cardResponse;
    }

    @GetMapping("/except")
    public void nullExceptTest() throws Exception {
        throw new Exception();
    }
}
