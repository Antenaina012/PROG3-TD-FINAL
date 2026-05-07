package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAccountDetail {
    private String id;
    private String type = "CASH";
    private Double amount;
}