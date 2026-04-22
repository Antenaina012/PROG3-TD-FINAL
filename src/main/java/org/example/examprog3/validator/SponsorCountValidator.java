package org.example.examprog3.validator;

import org.example.examprog3.entity.Member;
import org.example.examprog3.exeption.InsufficientSponsorCount;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SponsorCountValidator {
    public void validate(List<Member> referees, String targetCollectivityId) {
        if (referees == null || referees.size() < 2) {
            throw new InsufficientSponsorCount("Il faut au moins 2 parrains.");
        }

        long internal = referees.stream()
                .filter(r -> r.getCollectivity() != null && r.getCollectivity().getId().equals(targetCollectivityId))
                .count();

        if (internal < (referees.size() - internal)) {
            throw new InsufficientSponsorCount("Parrains internes >= Parrains externes requis.");
        }
    }
}