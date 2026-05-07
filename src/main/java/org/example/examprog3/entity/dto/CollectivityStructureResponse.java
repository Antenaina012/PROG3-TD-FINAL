package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityStructureResponse {
    private MemberResponse president;
    private MemberResponse vicePresident;
    private MemberResponse treasurer;
    private MemberResponse secretary;
}