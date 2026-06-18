package com.example.day5.initializer;

import com.example.day5.entity.Member;
import com.example.day5.entity.Order;
import com.example.day5.repository.MemberRepository;
import com.example.day5.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@org.springframework.core.annotation.Order(1)
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            Member member = memberRepository.save(Member.builder()
                    .name("회원" + i).email("email.com" + i).age(20 + i).build());

            for (int k = 0; k < 3; k++) {
                orderRepository.save(Order.builder()
                        .member(member).build());
            }
        }
    }
}
