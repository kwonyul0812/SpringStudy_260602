package com.example.day2.service;

import com.example.day2.dto.MemberDto;
import com.example.day2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public int createMember(MemberDto member) {
        int num = memberRepository.save(member);
        return num;
    }

    public MemberDto findById(Long id) {
        return memberRepository.findById(id);
    }

    public List<MemberDto> findAll() {
        return memberRepository.findAll();

    }
}
