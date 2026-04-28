package org.example.examprog3.service;

import lombok.AllArgsConstructor;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.entity.dto.MemberResponse;
import org.example.examprog3.repository.MemberRepository;
import org.example.examprog3.validator.CollectivityRuleValidator;
import org.example.examprog3.validator.PaymentValidator;
import org.example.examprog3.validator.SponsorCountValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PaymentValidator paymentValidator;
    private final CollectivityRuleValidator collectivityRuleValidator;
    private final SponsorCountValidator sponsorCountValidator;

    public List<MemberResponse> createMembers(List<CreateMember> memberList) {

        memberList.forEach(m -> {
            // 1. Validation du paiement
            paymentValidator.validate(m);

            // 2. Validation des parrains (Sponsors)
            // On ne valide QUE si la liste n'est pas vide.
            // Si elle est vide, c'est un membre fondateur.
            if (m.getReferees() != null && !m.getReferees().isEmpty()) {
                sponsorCountValidator.validate(m);
            }
        });

        // Extraction des IDs de parrains
        List<String> sponsorIds = memberList.stream()
                .filter(m -> m.getReferees() != null)
                .flatMap(m -> m.getReferees().stream())
                .distinct()
                .toList();

        List<Member> sponsors = sponsorIds.isEmpty() ?
                new ArrayList<>() :
                repository.findByIds(sponsorIds);

        memberList.forEach(m ->
                collectivityRuleValidator.validate(m, sponsors)
        );

        List<Member> members = memberList.stream()
                .map(this::toEntity)
                .toList();

        List<Member> saved = repository.saveAll(members, memberList);

        return buildResponse(saved, memberList);
    }

    private Member toEntity(CreateMember dto) {
        return Member.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .profession(dto.getProfession())
                .enrolmentDate(dto.getEnrolmentDate() != null ? dto.getEnrolmentDate() : Instant.now())
                .build();
    }

    private List<MemberResponse> buildResponse(List<Member> saved, List<CreateMember> dtos) {
        return IntStream.range(0, saved.size())
                .mapToObj(i -> {
                    Member m = saved.get(i);
                    CreateMember dto = dtos.get(i);

                    return MemberResponse.builder()
                            .id(m.getId())
                            .firstName(m.getFirstName())
                            .lastName(m.getLastName())
                            .birthDate(m.getBirthDate())
                            .gender(m.getGender())
                            .address(m.getAddress())
                            .profession(m.getProfession())
                            .phoneNumber(m.getPhoneNumber())
                            .email(m.getEmail())
                            .referees(dto.getReferees() != null ? dto.getReferees() : new ArrayList<>())
                            .build();
                })
                .toList();
    }
}