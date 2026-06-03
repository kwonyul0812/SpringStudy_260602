package com.example.day2.controller;

import com.example.day2.dto.MemberDto;
import com.example.day2.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {

    private final GreetingService greetingService;

    @Autowired
    public GreetController(@Qualifier("ko") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greet/{name}")
    public String greeting(@PathVariable String name) {
        return greetingService.greet(name);
    }

    @GetMapping("/testDto")
    public void testDto() {
        MemberDto member = MemberDto.builder()
                .id(1L)
                .name("홍길동")
                .email("h@a.com")
                .build();

        System.out.println(member);
    }
}
