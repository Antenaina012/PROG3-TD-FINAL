package org.example.examprog3.Service;

import lombok.RequiredArgsConstructor;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.repository.MemberRepository;
import org.example.examprog3.validator.MemberValidator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;
    private final Connection connection;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> enrollMembers(List<CreateMember> dtos) {
        List<Member> createdMembers = new ArrayList<>();
        try {
            connection.setAutoCommit(false); // Début transaction manuelle

            for (CreateMember dto : dtos) {
                List<Member> referees = memberRepository.findAllById(dto.refereeIds());
                memberValidator.validateAdmission(dto, referees);

                Member member = mapToEntity(dto);
                memberRepository.save(member);
                createdMembers.add(member);
            }

            connection.commit();
        } catch (Exception e) {
            try { connection.rollback(); } catch (SQLException se) { throw new RuntimeException(se); }
            throw new RuntimeException("Enrollment failed: " + e.getMessage());
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
        return createdMembers;
    }

    private Member mapToEntity(CreateMember dto) {
        Member m = new Member();
        m.setId(UUID.randomUUID().toString());
        m.setFirstName(dto.firstName());
        m.setLastName(dto.lastName());
        m.setBirthDate(dto.birthDate());
        m.setGender(dto.gender());
        m.setAddress(dto.address());
        m.setProfession(dto.profession());
        m.setPhoneNumber(dto.phoneNumber());
        m.setEmail(dto.email());
        m.setOccupation(dto.occupation());
        m.setJoinDate(java.time.LocalDate.now());

        if (dto.collectivityId() != null) {
            Collectivity c = new Collectivity();
            c.setId(dto.collectivityId());
            m.setCollectivity(c);
        }
        return m;
    }
}