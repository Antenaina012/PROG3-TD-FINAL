package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.ActivityType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String id;
    private Collectivity collectivity;
    private String label;
    private ActivityType activityType;
    private List<String> memberOccupationConcerned;
    private Integer weekOrdinal;
    private String dayOfWeek;
    private LocalDate executiveDate;
    private LocalDate creationDate;
}