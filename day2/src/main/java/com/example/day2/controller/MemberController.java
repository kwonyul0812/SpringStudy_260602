package com.example.day2.controller;

import com.example.day2.dto.MemberDto;
import com.example.day2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public void create() {
        MemberDto member = new MemberDto(1L, "qq", "kwon@naver.com");
        int num = memberService.createMember(member);
        if (num == 1) {
            System.out.println("회원 생성");
        } else {
            System.out.println("오류");
        }
    }

    @GetMapping("/members/{id}")
    public void getById(@PathVariable Long id) {
        MemberDto member = memberService.findById(id);
        System.out.println(member);
    }

    @GetMapping("/members/all")
    public void getAll() {
        List<MemberDto> list = memberService.findAll();

        System.out.println(list);
    }

}
