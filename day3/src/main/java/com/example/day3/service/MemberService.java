package com.example.day3.service;

import com.example.day3.dto.MemberCreateRequest;
import com.example.day3.dto.MemberResponse;
import com.example.day3.dto.MemberUpdateRequest;
import com.example.day3.repository.Member;
import com.example.day3.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse findOne(Long id) {
        Member member =  memberRepository.findById(id);

        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .createdAt(member.getCreateAt())
                .build();
    }

    public MemberResponse create(@Valid MemberCreateRequest req) {
        return memberRepository.save(req);
    }

    public List<MemberResponse> findAll(int page, int size) {
        List<MemberResponse> memberResponses = memberRepository.findAll(page, size);
        if (memberResponses.isEmpty()) {
            System.out.println("해당 범위에 해당하는 멤버가 없습니다.");
        }
        return memberResponses;
    }

    public MemberResponse update(Long id, @Valid MemberUpdateRequest req) {
        Member member = memberRepository.updateById(id, req);
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .createdAt(member.getCreateAt())
                .build();

    }


    public void delete(Long id) {
        Member member = memberRepository.findById(id);
        memberRepository.deleteById(member.getId());
    }
}
