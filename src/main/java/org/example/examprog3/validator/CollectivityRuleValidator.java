package org.example.examprog3.validator;

import java.util.List;

import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.MemberCollectivity;
import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.entity.enums.CollectivityOccupation;
import org.example.examprog3.exception.InsufficientSponsorCount;
import org.springframework.stereotype.Component;

@Component
public class CollectivityRuleValidator {

    public void validate(CreateMember dto, List<Member> sponsors) {

        int inTargetCollectivity = 0;
        int inOtherCollectivities = 0;

        for (Member sponsor : sponsors) {

            if (!dto.getReferees().contains(sponsor.getId())) {
                continue;
            }

            // Vérifier que le parrain est un membre confirmé (pas JUNIOR)
            // Selon section B-2: "Être parrainé par au moins deux membres confirmés"
            if (!isValidSponsor(sponsor, dto.getCollectivityIdentifier())) {
                throw new InsufficientSponsorCount(
                        "Le parrain " + sponsor.getFirstName() + " " + sponsor.getLastName() +
                        " n'est pas un membre confirmé de la collectivité cible"
                );
            }

            List<String> collectivityIds =
                    sponsor.getIdsOfActualBelongingCollectivities();

            if (collectivityIds.contains(dto.getCollectivityIdentifier())) {
                inTargetCollectivity++;
            } else {
                inOtherCollectivities++;
            }
        }

        if (inTargetCollectivity < inOtherCollectivities) {
            throw new InsufficientSponsorCount(
                    dto.getFirstName() +
                            " does not satisfy collectivity sponsor rule"
            );
        }
    }

    /**
     * Vérifie si un membre peut être parrain.
     * Selon section B-2, seul un membre confirmé (non JUNIOR) peut parrainer.
     */
    private boolean isValidSponsor(Member sponsor, String targetCollectivityId) {
        // Trouver l'occupation du parrain dans la collectivité cible
        return sponsor.getMemberCollectivities().stream()
                .filter(mc -> mc.getCollectivity().getId().equals(targetCollectivityId))
                .filter(mc -> mc.getEndDate() == null) // Membership actif
                .map(MemberCollectivity::getOccupation)
                .anyMatch(occupation -> occupation != CollectivityOccupation.JUNIOR);
    }
}