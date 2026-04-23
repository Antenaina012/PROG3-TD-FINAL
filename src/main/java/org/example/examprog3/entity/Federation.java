package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Federation {
    private Integer id;
    private double contributionPercentage;
    private Structure structure;
}