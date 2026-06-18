package com.example.day5.service;

import com.example.day5.dto.MemberCreateRequest;
import com.example.day5.dto.MemberResponse;
import com.example.day5.entity.Member;
import com.example.day5.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class MemberOuterService {

    private final MemberService service;
    private final MemberRepository memberRepository;
    private final DataSource dataSource;


    @Transactional()
    public MemberResponse testOuter(MemberCreateRequest request) {
        MemberResponse memberResponse = service.create(request);
        Member member = memberRepository.save(Member.builder().name("kim")
                .email("poi@poi.com").age(15).build());
        if (member != null) {
            throw new RuntimeException("runtime error");
        }
        return memberResponse;
    }

    public void manualJdbc() throws SQLException {
        boolean test = true;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()) {


            if (test) {
                throw new RuntimeException();
            }

//            rs.close();
//            ps.close();
//            conn.close();
        }
    }


}
