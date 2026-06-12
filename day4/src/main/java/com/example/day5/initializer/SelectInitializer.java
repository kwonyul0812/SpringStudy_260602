package com.example.day5.initializer;

import com.example.day5.entity.Member;
import com.example.day5.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@org.springframework.core.annotation.Order(2)
@RequiredArgsConstructor
public class SelectInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Member> members = memberRepository.findAllWithOrders();

        for (Member member : members) {
            System.out.println(member.getName() + " 주문 수: " +
                    member.getOrders().size());
        }
    }
}
