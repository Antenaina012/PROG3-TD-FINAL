package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.ActivityStatus;
import org.example.examprog3.entity.enums.Frequency;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotisationPlan {
    private String id;
    private String label;
    private Collectivity collectivity;
    private ActivityStatus status;
    private Frequency frequency;
    private LocalDate eligibleFrom;
    private Double amount;
}