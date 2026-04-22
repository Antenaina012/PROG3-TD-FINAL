package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.examprog3.entity.enums.PaymentMode;
import org.example.examprog3.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private Integer id;
    private Integer memberId;
    private Integer collectivityId;
    private Integer cotisationPlanId;
    private Integer accountId;
    private BigDecimal amount;
    private PaymentMode paymentMode;
    private String description;
    private TransactionType transactionType;
    private Date transactionDate;
}