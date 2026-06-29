package com.example.day5.controller;

import com.example.day5.dto.MemberCreateRequest;
import com.example.day5.dto.MemberResponse;
import com.example.day5.dto.MemberUpdateRequest;
import com.example.day5.service.MemberOuterService;
import com.example.day5.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberOuterService memberOuterService;

    @Autowired
    public MemberController(MemberService memberService, MemberOuterService memberOuterService) {
        this.memberService = memberService;
        this.memberOuterService = memberOuterService;
    }

//    @PostMapping
//    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberCreateRequest request) throws IOException {
//        MemberResponse memberResponse = memberService.create(request);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(memberResponse);
//    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberCreateRequest request) {
        MemberResponse memberResponse = memberOuterService.testOuter(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberResponse);
    }

    // 조회
    @GetMapping("/{id}")
    public MemberResponse findOne(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @GetMapping
    public List<MemberResponse> findAll() {
        List<MemberResponse> memberResponseList = memberService.findAll();
        return memberResponseList;
    }

    @GetMapping(value = "/search", params = "email") // 이메일로 조회
    public MemberResponse findEmail(@RequestParam(name = "email") @NotBlank @Email String email) {
        return memberService.findEmail(email);
    }

    @GetMapping(value = "/search", params = "keyword") // 키워드로 이름 조회
    public MemberResponse findNameContaining(@RequestParam(name = "keyword") @NotBlank String keyword) {
        return memberService.findByNameContaining(keyword);
    }

    @GetMapping(value = "/search", params = "age") // 나이가 더 많은 회원들 조회
    public List<MemberResponse> findAgeGreaterThanEqual(@RequestParam(name = "age") @Min(0) @Max(150) int age) {
        List<MemberResponse> memberResponseList = memberService.findAgeGreaterThanEqual(age);
        return memberResponseList;
    }

    @GetMapping(value = "/search", params = "domain") // 특정 도메인으로 끝나는 이메일 개수 조회
    public ResponseEntity<Long> countEmailEndingWith(@RequestParam(name = "domain") @NotBlank String domain) {
        Long count = memberService.countByEmailEndingWith(domain);
        return ResponseEntity.ok().body(count);
    }


    // 수정
    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @RequestBody @Valid MemberUpdateRequest request) {
        return memberService.update(id, request);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
