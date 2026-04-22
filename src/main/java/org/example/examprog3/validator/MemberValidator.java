package org.example.examprog3.validator;

import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exeption.InsufficientSponsorCount;
import org.example.examprog3.exeption.PaymentException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MemberValidator {
    public void validateAdmission(CreateMember dto, List<Member> referees) {
        if (referees == null || referees.size() < 2) {
            throw new InsufficientSponsorCount("Admission requires at least 2 confirmed referees.");
        }

        long internal = referees.stream()
                .filter(r -> r.getCollectivity() != null && r.getCollectivity().getId().equals(dto.collectivityId()))
                .count();
        long external = referees.size() - internal;

        if (internal < external) {
            throw new InsufficientSponsorCount("Number of internal referees must be >= external ones.");
        }

        if (!dto.registrationFeePaid() || !dto.membershipDuesPaid()) {
            throw new PaymentException("Registration fees and annual dues must be fully paid.");
        }
    }
}