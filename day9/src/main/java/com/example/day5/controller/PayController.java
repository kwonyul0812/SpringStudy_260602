package com.example.day5.controller;


import com.example.day5.dto.PaymentEvent;
import com.example.day5.dto.TransactionEvent;
import com.example.day5.service.PaymentEventPublisher;
import com.example.day5.service.TransactionEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PayController {

    private final PaymentEventPublisher paymentEventPublisher;
    private final TransactionEventProducer transactionEventProducer;

    @Autowired
    public PayController(PaymentEventPublisher paymentEventPublisher, TransactionEventProducer transactionEventProducer) {
        this.paymentEventPublisher = paymentEventPublisher;
        this.transactionEventProducer = transactionEventProducer;
    }

    @PostMapping
    public void publish(@RequestBody PaymentEvent event) {
        paymentEventPublisher.publish(event);
    }

    @PostMapping("/transaction")
    public void transactionEvent(@RequestBody TransactionEvent event) throws Exception {
        transactionEventProducer.publish(event);
    }
}
