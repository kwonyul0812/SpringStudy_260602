package com.example.day7.controller;

import com.example.day7.dto.MemberRequest;
import com.example.day7.dto.MemberResponse;
import com.example.day7.dto.UserResponse;
import com.example.day7.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {

    private final AsyncService asyncService;

    @Autowired
    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping
    public void test() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            asyncService.asyncTest();
        }
        Long elapsed = System.currentTimeMillis() - start;
        log.info("총 흐른 시간 {}ms", elapsed);
    }

    //    @GetMapping("/retry")
//    public User retry() {
//        User response = asyncService.callExternal();
//        return response;
//    }
    @GetMapping("/retry")
    public UserResponse retry() {
        return asyncService.callExternal();
    }

    @GetMapping("/timeout")
    public String timeout() {
        return asyncService.callSlowApiLocal();
    }

    @PostMapping("/create")
    public void create(@RequestBody MemberRequest memberRequest) throws ExecutionException, InterruptedException {
//        MemberResponse memberResponse = asyncService.createMember(memberRequest);
        List<MemberResponse> memberList = asyncService.createMembers();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (MemberResponse memberResponse : memberList) {
            futures.add(asyncService.sendCongrats(memberResponse));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
