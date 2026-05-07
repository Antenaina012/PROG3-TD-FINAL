package org.example.examprog3.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCollectivity {
    private String id;
    private Member member;
    private Collectivity collectivity;
    private LocalDate beginDate;
    private LocalDate endDate;
}
