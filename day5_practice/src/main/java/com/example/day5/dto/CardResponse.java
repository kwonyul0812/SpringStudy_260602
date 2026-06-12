package com.example.day5.dto;

import com.example.day5.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CardResponse {
    private Long id;
    private String cardNo;
    private BigDecimal balance;

    public static CardResponse from(Card card) {
        return new CardResponse(card.getId(),
                card.getCardNo(),
                card.getBalance());
    }
}
