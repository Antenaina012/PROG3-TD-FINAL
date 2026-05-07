package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.PaymentMode;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPaymentResponse {
    private String id;
    private Integer amount;
    private PaymentMode paymentMode;
    private FinancialAccountResponse accountCredited;
    private LocalDate creationDate;
}