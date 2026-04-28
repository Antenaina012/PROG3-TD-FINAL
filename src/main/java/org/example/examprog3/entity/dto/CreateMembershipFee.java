package org.example.examprog3.entity.dto;

import lombok.Builder;
import lombok.Data;
import org.example.examprog3.entity.enums.Frequency;

@Data
@Builder
public class CreateMembershipFee {
    private String label;
    private Double amount;
    private Frequency frequency;
}