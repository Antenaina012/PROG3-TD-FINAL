package org.example.examprog3.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.example.examprog3.entity.enums.PaymentMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPaymentResponse {
    private String id;
    private BigDecimal amount;
    private LocalDate creationDate;
    private PaymentMode paymentMode;
}
