package org.example.examprog3.validator;


import java.util.List;

import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exception.InsufficientSponsorCount;
import org.springframework.stereotype.Component;

@Component
public class SponsorCountValidator {
    public void validate(CreateMember member) {
        if (member.getReferees() == null || member.getReferees().size() < 2) {
            throw new InsufficientSponsorCount(
                    member.getFirstName() + " : at least two sponsors required"
            );
        }
    }

    /**
     * Valide que tous les parrains ont une ancienneté d'au moins 90 jours.
     * Selon section B: "Être parrainé par un membre confirmé dont l'ancienneté dépasse les 90 jours"
     */
    public void validateSponsorSeniority(List<Member> sponsors) {
        for (Member sponsor : sponsors) {
            if (!sponsor.isAValidSponsor()) {
                throw new InsufficientSponsorCount(
                        "Le parrain " + sponsor.getFirstName() + " " + sponsor.getLastName() +
                        " n'a pas l'ancienneté requise de 90 jours"
                );
            }
        }
    }

    public void validate(List<CreateMember> members) {
        for (CreateMember member : members) {
            validate(member);
        }
    }
}
