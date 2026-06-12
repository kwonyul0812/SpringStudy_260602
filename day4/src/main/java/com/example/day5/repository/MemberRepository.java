package com.example.day5.repository;

import com.example.day5.entity.Member;
import com.example.day5.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNameContaining(String keyword);

    List<Member> findByAgeGreaterThanEqual(int age);

    long countByEmailEndingWith(String domain);

//    @Query("SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.orders")
//    List<Member> findAllWithOrders();

    @EntityGraph(attributePaths = {"orders"})
    @Query("SELECT m FROM Member m")
    List<Member> findAllWithOrders();
}
