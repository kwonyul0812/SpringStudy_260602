package com.example.day5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TransactionEvent {

    private Long id;
    private BigDecimal amount;
    private String timestamp;
}
