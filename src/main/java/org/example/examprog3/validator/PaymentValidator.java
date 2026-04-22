package org.example.examprog3.validator;

import org.example.examprog3.entity.dto.CreateMember;
import org.example.examprog3.exeption.PaymentException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaymentValidator {
    public void validate(CreateMember member) {
        if (!member.isMembershipDuesPaid() || !member.isRegistrationFeePaid()) {
            throw new PaymentException(
                    member.getFirstName() + " : payment not completed"
            );
        }
    }
    public void validate(List<CreateMember> members) {
        for (CreateMember member : members) {
            validate(member);
        }
    }
}
