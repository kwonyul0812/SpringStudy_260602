package com.example.day5.dto;

import com.example.day5.entity.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CardCreateRequest {

    @NotBlank()
    @Pattern(regexp = "\\d{16}", message = "카드번호는 16자리 입니다.")
    private String cardNo;

    @NotNull
    @PositiveOrZero(message = "잔액은 0원 이상이여야 합니다.")
    private BigDecimal balance;

    public Card toEntity() {
        return new Card().builder()
                .cardNo(cardNo).balance(balance).build();
    }
}
