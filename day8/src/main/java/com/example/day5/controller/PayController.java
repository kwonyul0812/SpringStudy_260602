package com.example.day5.controller;


import com.example.day5.dto.PaymentEvent;
import com.example.day5.service.PaymentEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PayController {

    private final PaymentEventPublisher paymentEventPublisher;

    @Autowired
    public PayController(PaymentEventPublisher paymentEventPublisher) {
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @PostMapping
    public void publish(@RequestBody PaymentEvent event) {
        paymentEventPublisher.publish(event);
    }
}
