package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.Bank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDetail {
    private String id;
    private String type = "BANK";
    private Double amount;
    private String holderName;
    private Bank bankName;
    private String bankCode;
    private String bankBranchCode;
    private String bankAccountNumber;
    private String bankAccountKey;
}