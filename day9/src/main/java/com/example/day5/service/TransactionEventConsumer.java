package com.example.day5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionEventConsumer {

    @KafkaListener(topics = "transaction-events", groupId = "stat-app")
    public void consume(String message) {
        log.info("거래 이벤트: {}", message);
    }
}
