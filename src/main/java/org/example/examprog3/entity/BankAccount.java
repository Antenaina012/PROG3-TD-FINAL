package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.Bank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private String id;
    private Account account;
    private String holderName;
    private Bank bankName;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String ribKey;
}