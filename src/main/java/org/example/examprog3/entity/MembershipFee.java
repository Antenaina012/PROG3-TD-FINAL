package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.Frequency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipFee {
    private String id;
    private String collectivityId;
    private String label; // ex: "Cotisation Standard 2026"
    private Double amount;
    private Frequency frequency;
}