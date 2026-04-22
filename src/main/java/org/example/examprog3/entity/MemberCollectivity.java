package org.example.examprog3.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.CollectivityOccupation;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCollectivity {
    private int id;
    private Member member;
    private Collectivity collectivity;
    private CollectivityOccupation occupation;
    private Instant startDate;
    private Instant endDate;
}