package com.example.day5.service;

import com.example.day5.aop.MaskLog;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @MaskLog(fields = {"cardNo", "accoutNo"})
    public void processPayment(String cardNo, String accountNo, BigDecimal amount) {
        // ...
    }
}
