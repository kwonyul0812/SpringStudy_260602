package com.example.day5.repository;

import com.example.day5.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<com.example.day5.entity.Card, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT c FROM Card c WHERE c.id = :id")
//    Optional<Card> findByIdForUpdate(@Param("id") Long id);
}
