package com.example.day5.repository;

import com.example.day5.dto.MemberCreateRequest;
import com.example.day5.dto.MemberResponse;
import com.example.day5.dto.MemberUpdateRequest;
import com.example.day5.entity.Member;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    private Long seq = 0L;
    Map<Long, Member> members = new HashMap<>();


    public MemberResponse save(@Valid MemberCreateRequest req) {
        Member member = new Member(seq, req.getName(), req.getEmail(), req.getAge(), LocalDateTime.now());
        members.put(seq, member);
        seq++;

        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .createdAt(member.getCreateAt())
                .build();

        return memberResponse;
    }

    public Member findById(Long id) {
        Member member = members.get(id);
        if (member == null) {
            throw new RuntimeException("Member not found: " + id);
        }

        return member;

    }

    public List<MemberResponse> findAll(int page, int size) {
        List<Member> memberList = new ArrayList<>(members.values());
        memberList.sort(Comparator.comparing(member -> member.getId()));


        int startIndex = page * size; // page : 몇페이지 인지, size: 몇개씩 보여줄지
        int totalCount = memberList.size();
        int endIndex = Math.min(startIndex + size, totalCount);

        if (startIndex >= totalCount) {
            return new ArrayList<>(); // page, index를 다시 확인해주세요
        }

        List<MemberResponse> memberResponses = memberList.subList(startIndex, endIndex).stream()
                .map(member -> MemberResponse.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .age(member.getAge())
                        .createdAt(member.getCreateAt())
                        .build()).collect(Collectors.toList());

        return memberResponses;
    }

    public Member updateById(Long id, @Valid MemberUpdateRequest req) {
        Member member = members.get(id);
        if(member == null) {
            throw new RuntimeException("Member not found: " + id);
        }
        member.setName(req.getName());
        member.setEmail(req.getEmail());

        return member;

    }

    public void deleteById(Long id) {
        members.remove(id);
    }

//    public List<MemberResponse> findAll(int page, int size) {
//    }
}
