package org.example.examprog3.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.Bank;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BankAccountResponse extends FinancialAccountResponse {
    private String type = "BANK";
    private String holderName;
    private Bank bankName;
    private String bankCode;
    private String bankBranchCode;
    private String bankAccountNumber;
    private String bankAccountKey;

    @Builder
    public BankAccountResponse(String id, Double amount, String holderName, Bank bankName,
                                String bankCode, String bankBranchCode,
                                String bankAccountNumber, String bankAccountKey) {
        super(id, amount);
        this.holderName = holderName;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankBranchCode = bankBranchCode;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountKey = bankAccountKey;
    }
}