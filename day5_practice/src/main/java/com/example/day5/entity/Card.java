package com.example.day5.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CARD_NO", nullable = false, length = 16, unique = true)
    private String cardNo;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;

    @Builder
    private Card(String cardNo, BigDecimal balance) {
        this.cardNo = cardNo;
        this.balance = balance;
    }

    public void deductBalance(BigDecimal deductBalance) {
        if (deductBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("차감할 금액은 0보다 커야합니다.");
        }
        if (balance.compareTo(deductBalance) < 0) {
            throw new IllegalArgumentException("차감 후 금액은 0보다 작을수 없습니다.");
        }
        balance = balance.subtract(deductBalance);
    }
}
