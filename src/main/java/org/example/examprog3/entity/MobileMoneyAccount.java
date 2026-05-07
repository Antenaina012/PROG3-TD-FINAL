package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.MobileBankingService;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileMoneyAccount {
    private String id;
    private Account account;
    private String holderName;
    private MobileBankingService serviceName;
    private String phoneNumber;
}