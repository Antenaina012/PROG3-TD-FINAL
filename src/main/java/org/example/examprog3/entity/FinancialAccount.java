package org.example.examprog3.entity;

import java.math.BigDecimal;

import org.example.examprog3.entity.enums.Bank;
import org.example.examprog3.entity.enums.MobileBankingService;
import org.example.examprog3.entity.enums.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String bankAccountNumber; // Doit être String selon la spec (23 chiffres)
    private Integer bankAccountKey;
}