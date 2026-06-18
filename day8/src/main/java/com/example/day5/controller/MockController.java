package com.example.day5.controller;

import com.example.day5.service.MemberOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/mock")
public class MockController {
    private final MemberOuterService memberOuterService;

    @Autowired
    public MockController(MemberOuterService memberOuterService) {
        this.memberOuterService = memberOuterService;
    }


    @GetMapping("/delay/{time}")
    public ResponseEntity<Void> delay(@PathVariable Long time) throws InterruptedException {
        Thread.sleep(time * 1000);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jdbcLeak")
    public void leakTest() throws SQLException {
        memberOuterService.manualJdbc();
    }
}
