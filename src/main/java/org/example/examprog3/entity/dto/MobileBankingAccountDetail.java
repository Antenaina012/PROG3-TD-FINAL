package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.MobileBankingService;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileBankingAccountDetail {
    private String id;
    private String type = "MOBILE_BANKING";
    private Double amount;
    private String holderName;
    private MobileBankingService mobileBankingService;
    private String mobileNumber;
}