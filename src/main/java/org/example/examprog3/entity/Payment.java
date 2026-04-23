package org.example.examprog3.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.examprog3.entity.enums.PaymentMode;
import org.example.examprog3.entity.enums.PaymentType;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@Builder
public class Payment {
    private String id;
    private String  memberId;
    private String collectivityId;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private BigDecimal amount;
    private PaymentMode paymentMode;
    private String description;

    private PaymentType transactionType;
    private Date transactionDate;
}