package org.example.examprog3.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CashAccountResponse extends FinancialAccountResponse {
    private String type = "CASH";
}