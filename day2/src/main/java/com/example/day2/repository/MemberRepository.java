package com.example.day2.repository;

import com.example.day2.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    List<MemberDto> list = Arrays.asList(
            new MemberDto(1L, "kwon", "kwonyul@aa"),
            new MemberDto(2L, "park", "park@AAA")
    );
    Map<Long, MemberDto> members = list.stream().collect(Collectors.toMap(MemberDto::getId, dto -> dto));

    public int save(MemberDto member) {
        if (member == null) {
            throw new IllegalArgumentException("member 가 null 입니다.");
        }

        if (member.getName() == null || member.getName().isEmpty()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
        if (member.getEmail() == null || member.getEmail().isEmpty()) {
            throw new IllegalArgumentException("이메일이 비어있습니다.");
        }

        return 1;
    }

    public MemberDto findById(Long id) {
        return members.get(id);
    }

    public List<MemberDto> findAll() {
        return members.values().stream().sorted(Comparator.comparing(MemberDto::getId)).collect(Collectors.toList());
    }
}
