package org.example.examprog3.entity;

import lombok.*;
import org.example.examprog3.entity.enums.Bank;
import org.example.examprog3.entity.enums.MobileBankingService;
import org.example.examprog3.entity.enums.PaymentMode;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialAccount {
    private String id;
    private String label;
    private PaymentMode type;
    private BigDecimal balance;

    private MobileBankingService mobileBankingService; // Pour MobileBankingAccount
    private String mobileNumber;

    private Bank bankName; // Pour BankAccount
    private String holderName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Integer bankAccountNumber;
    private Integer bankAccountKey;
}