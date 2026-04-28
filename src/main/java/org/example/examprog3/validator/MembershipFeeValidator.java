package org.example.examprog3.validator;

import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.dto.CreateMembershipFee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipFeeValidator {
    public void validate(List<CreateMembershipFee> fees) throws BadRequestException {
        if (fees == null || fees.isEmpty()) {
            throw new BadRequestException("La liste des frais ne peut pas être vide.");
        }
        for (CreateMembershipFee fee : fees) {
            if (fee.getAmount() == null || fee.getAmount() <= 0) {
                throw new BadRequestException("Le montant du frais '" + fee.getLabel() + "' doit être supérieur à 0.");
            }
            if (fee.getLabel() == null || fee.getLabel().isBlank()) {
                throw new BadRequestException("Le libellé (label) est obligatoire.");
            }
            if (fee.getFrequency() == null) {
                throw new BadRequestException("La fréquence est obligatoire (WEEKLY, MONTHLY...).");
            }
        }
    }
}