package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Federation {
    private String id;
    private String name;
    private String headquarterLocation;
}