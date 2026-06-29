package com.example.day5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class PaymentEvent {

    private Long id;
    private BigDecimal amount;
    private String timestamp;
}
