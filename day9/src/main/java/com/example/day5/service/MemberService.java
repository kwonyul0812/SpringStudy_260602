package com.example.day5.service;

import com.example.day5.dto.MemberCreateRequest;
import com.example.day5.dto.MemberResponse;
import com.example.day5.dto.MemberUpdateRequest;
import com.example.day5.entity.Member;
import com.example.day5.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MemberResponse create(MemberCreateRequest request) {
        Member member = request.toEntity();
        Member saved = memberRepository.save(member);
        return MemberResponse.from(saved);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "member", key = "#id")
    public MemberResponse findOne(Long id) {
        log.info("회원 조회");
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponseList = members.stream()
                .map(member -> MemberResponse.from(member))
                .collect(Collectors.toList());

        return memberResponseList;
    }

    @Transactional
    @CacheEvict(value = "member", key = "#id")
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));
        member.updateInfo(request.getName(), request.getEmail()); // setter 역할 하는 메소드

        memberRepository.save(member);
        return MemberResponse.from(member);

    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberResponse findEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));

        return MemberResponse.from(member);
    }

    public MemberResponse findByNameContaining(String keyword) {
        Member member = memberRepository.findByNameContaining(keyword)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));

        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAgeGreaterThanEqual(int age) {
        List<MemberResponse> memberResponseList = memberRepository.findByAgeGreaterThanEqual(age)
                .stream()
                .map(member -> MemberResponse.from(member))
                .collect(Collectors.toList());

        return memberResponseList;
    }

    public Long countByEmailEndingWith(String domain) {
        Long count = memberRepository.countByEmailEndingWith(domain);
        return count;
    }
}
