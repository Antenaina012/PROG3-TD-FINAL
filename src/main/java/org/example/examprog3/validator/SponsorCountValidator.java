package org.example.examprog3.validator;


import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exception.InsufficientSponsorCount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorCountValidator {
    public void validate(CreateMember member) {
        if (member.getReferees() == null || member.getReferees().size() < 2) {
            throw new InsufficientSponsorCount(
                    member.getFirstName() + " : at least two sponsors required"
            );
        }
    }

    public void validate(List<CreateMember> members) {
        for (CreateMember member : members) {
            validate(member);
        }
    }
}
