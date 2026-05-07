package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.PaymentMode;
import org.example.examprog3.entity.enums.TransactionType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private Collectivity collectivity;
    private Member member;
    private CotisationPlan cotisationPlan;
    private TransactionType transactionType;
    private Double amount;
    private LocalDate transactionDate;
    private PaymentMode paymentMode;
    private String description;
    private Account account;
}