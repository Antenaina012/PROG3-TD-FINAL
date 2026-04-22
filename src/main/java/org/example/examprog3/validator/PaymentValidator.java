package org.example.examprog3.validator;

import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exeption.PaymentException;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {
    public void validate(CreateMember dto) {
        if (!dto.registrationFeePaid() || !dto.membershipDuesPaid()) {
            throw new PaymentException("Tous les frais (adhésion + cotisation) doivent être acquittés.");
        }
    }
}