package com.example.day7.controller;

import com.example.day7.dto.UserRequest;
import com.example.day7.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MockApiController {
    @PostMapping("/mock/users")
    public UserResponse createUser(@RequestBody UserRequest request) {
        // 받은 데이터로 응답 객체 만들어 반환
        return new UserResponse(
                1L,
                request.getName(),
                request.getAge(),
                request.getName() + "님 생성 완료"
        );
    }

    @GetMapping("/mock/slow")
    public String slow() throws InterruptedException {
        log.info("mock: 5초 지연 시작");
        Thread.sleep(5000);   // 5초 후 응답 (httpstat.us의 sleep=5000 역할)
        log.info("mock: 응답 반환");
        return "OK (5초 지연 후)";
    }

    @GetMapping("/mock/{name}")
    public String congrat(@PathVariable String name) {
        return name + "님 환영합니다!!";
    }
}
