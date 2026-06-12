package com.example.day5.controller;

import com.example.day5.entity.Card;
import com.example.day5.repository.CardRepository;
import com.example.day5.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final CardService cardService;
    private final CardRepository cardRepository;

    @Autowired
    public TestController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @PostMapping("/{cardId}")
    public void poolTest(@PathVariable Long cardId) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                int attempt = 0;
                while (attempt < 3) {
                    try {
                        cardService.deduct(cardId, new BigDecimal("1000"));
                        return;
                    } catch (ObjectOptimisticLockingFailureException e) {
                        log.error("OptimisticLock 에러, 재시도 {}번", attempt);
                        attempt++;
                    }
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        Card c = cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("회원 없음"));
        log.info("최종 잔액: {}", c.getBalance().stripTrailingZeros().toPlainString());
    }
}
