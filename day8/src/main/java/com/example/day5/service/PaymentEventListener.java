package com.example.day5.service;


import com.example.day5.config.RabbitConfig;
import com.example.day5.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentEventListener {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(PaymentEvent event) {
        log.info("결제 이벤트 수신: {}", event);
    }
}
