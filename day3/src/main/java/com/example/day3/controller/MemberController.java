package com.example.day3.controller;

import com.example.day3.dto.MemberCreateRequest;
import com.example.day3.dto.MemberResponse;
import com.example.day3.dto.MemberUpdateRequest;
import com.example.day3.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@Tag(name = "회원 API", description = "회원 CRUD")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 여러건 조회")
    @GetMapping
    public List<MemberResponse> findAll(@Parameter(description = "페이지") @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return memberService.findAll(page, size);
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    public MemberResponse findOne(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @Operation(summary = "회원 생성")
    @PostMapping // 201
    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberCreateRequest req) {
        MemberResponse saved = memberService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "회원 수정")
    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @RequestBody @Valid MemberUpdateRequest req) {
        return memberService.update(id, req);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}") // 204
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
