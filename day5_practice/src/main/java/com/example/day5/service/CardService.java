package com.example.day5.service;

import com.example.day5.annotation.MaskLog;
import com.example.day5.dto.CardCreateRequest;
import com.example.day5.dto.CardResponse;
import com.example.day5.entity.Card;
import com.example.day5.exception.MemberNotFoundException;
import com.example.day5.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@Slf4j
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public CardResponse create(CardCreateRequest request) {
        Card card = request.toEntity();
        Card saved = cardRepository.save(card);
        return CardResponse.from(saved);
    }

    @Transactional
    public void deductAll(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new MemberNotFoundException(cardId));
        card.deductBalance(card.getBalance());
        cardRepository.save(card);
    }

    @Transactional
    public void deduct(Long cardId, BigDecimal deductBalance) {
        Card c = cardRepository.findById(cardId).orElseThrow(() -> new MemberNotFoundException(cardId));
        c.deductBalance(deductBalance);
        cardRepository.save(c);
    }

    @Transactional
    @MaskLog(fields = {"cardNo"})
    public CardResponse find(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new MemberNotFoundException(cardId));
        CardResponse cardResponse = CardResponse.from(card);
        return cardResponse;
    }
}
