package com.example.day5.aop;

import com.example.day5.annotation.MaskLog;
import com.example.day5.dto.CardResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MaskingAspect {

    @AfterReturning(pointcut = "@annotation(maskLog)", returning = "result")
    public void cardNoMasking(MaskLog maskLog, CardResponse result) {
        if (result == null) return;
        result.setCardNo(mask(result.getCardNo()));
    }

    private String mask(String cardNo) {
        if (cardNo == null || cardNo.length() < 16) return cardNo;
        return cardNo.substring(0, 4) + "********" + cardNo.substring(cardNo.length() - 4);
    }
}
