package org.example.examprog3.entity;

import lombok.*;
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
}