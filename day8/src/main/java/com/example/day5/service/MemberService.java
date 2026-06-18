package com.example.day5.service;

import com.example.day5.dto.MemberCreateRequest;
import com.example.day5.dto.MemberResponse;
import com.example.day5.dto.MemberUpdateRequest;
import com.example.day5.entity.Member;
import com.example.day5.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.activation.DataSource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public MemberService(MemberRepository memberRepository, RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MemberResponse create(MemberCreateRequest request) {
        Member member = request.toEntity();
        Member saved = memberRepository.save(member);
        return MemberResponse.from(saved);
    }


    @Transactional
    public MemberResponse findOne(Long id) throws InterruptedException {
        Thread.sleep(2000);
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

    public void callApi() {
        ResponseEntity<Void> responseEntity = restTemplate.getForEntity("http://localhost:8080/mock/delay/3", Void.class);
    }

}
