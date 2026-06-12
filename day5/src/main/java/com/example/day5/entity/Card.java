package com.example.day5.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
public class Card {
    @Id
    private Long id;
    private BigDecimal balance;

    @Version // 낙관적 락 활성화, 엔티티 변경시마다 version 값 + 1
    private Long version;
    // UPDATE CARD SET balance = ?, version = version + 1
    // WHERE id = ? AND version = ?;
    // 와 동일한 작업, JPA 자동으로 생성함.

    // OptimisticLockException : 버전 불일치시 UPDATE 날릴시 반환되는 에러

}
