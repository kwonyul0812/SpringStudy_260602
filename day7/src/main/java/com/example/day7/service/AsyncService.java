package com.example.day7.service;

import com.example.day7.dto.MemberRequest;
import com.example.day7.dto.MemberResponse;
import com.example.day7.dto.UserRequest;
import com.example.day7.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncService {

    private final RestTemplate restTemplate;

    @Autowired
    public AsyncService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("notifyExecutor")
    public void asyncTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        log.info("작업 완료 {}", MDC.get("traceId"));
    }

    @Retryable(
            value = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public UserResponse callExternal() {
        log.info("외부 호출 시도");
//        String result = restTemplate.getForObject("http://naver.com", String.class);
//        return result;

        UserRequest user = new UserRequest("홍길동", 20);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Correlation-Id", MDC.get("traceId"));
        headers.set("Authorization", "Bearer " + "token");

        HttpEntity<UserRequest> entity = new HttpEntity<>(user, headers);
        ResponseEntity<UserResponse> res = restTemplate.exchange(
                "http://localhost:8080/mock/users",
                HttpMethod.POST,
                entity,
                UserResponse.class
        );
        return res.getBody();
    }

    public String callSlowApiLocal() {
        long start = System.currentTimeMillis();
        try {
            String result = restTemplate.getForObject(
                    "http://localhost:8080/mock/slow", String.class);  // http + localhost
            return "성공: " + result;
        } catch (ResourceAccessException e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("타임아웃 발생! {}ms 후 실패, 원인={}", elapsed, e.getMessage());
            return "ResourceAccessException 발생 (약 " + elapsed + "ms 후)";
        }
    }

    @Recover
    public String recover(ResourceAccessException e) {
        log.error("최종 실패 후 폴백", e);
        return "FAILED";
    }


    public void timeout() {
        String url = "https://httpstat.us/200?sleep=5000";
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        log.info("회원생성");
        return new MemberResponse(memberRequest.getId(), memberRequest.getName());
    }

    @Async("notifyExecutor")
    public CompletableFuture<Void> sendCongrats(MemberResponse memberResponse) {
        log.info("비동기 진입 {}", MDC.get("traceId"));
        String name = memberResponse.getName();
        String result = restTemplate.getForObject("http://localhost:8080/mock/{name}", String.class, name);
        log.info(result);

        return CompletableFuture.completedFuture(null);
    }

    public List<MemberResponse> createMembers() {
        List<MemberResponse> memberList = new ArrayList<>(Arrays.asList(new MemberResponse(1L, "홍길동"),
                new MemberResponse(2L, "박보검"),
                new MemberResponse(3L, "김철수"),
                new MemberResponse(4L, "강감찬"),
                new MemberResponse(5L, "세종대왕"),
                new MemberResponse(6L, "빅개발")
        ));
        return memberList;
    }
}
