package org.example.examprog3.validator;

import org.example.examprog3.entity.Member;
import org.example.examprog3.exeption.SponsorTenureException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class SponsorTenureValidator {
    public void validate(List<Member> referees) {
        for (Member referee : referees) {
            if (referee.getJoinDate().isAfter(LocalDate.now().minusMonths(6))) {
                throw new SponsorTenureException("Le parrain " + referee.getLastName() + " doit avoir au moins 6 mois d'ancienneté.");
            }
        }
    }
}