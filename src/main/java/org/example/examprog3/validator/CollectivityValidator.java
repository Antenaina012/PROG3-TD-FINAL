package org.example.examprog3.validator;

import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class CollectivityValidator {
    public void validateCreation(CreateCollectivity dto, List<Member> members) {
        if (!dto.federationApproval()) {
            throw new RuntimeException("Federation approval is required.");
        }
        if (members == null || members.size() < 10) {
            throw new RuntimeException("At least 10 members are required.");
        }
        long seniors = members.stream()
                .filter(m -> m.getJoinDate() != null && m.getJoinDate().isBefore(LocalDate.now().minusMonths(6)))
                .count();
        if (seniors < 5) {
            throw new RuntimeException("At least 5 members must have 6 months of seniority.");
        }
    }
}