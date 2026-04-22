package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collectivity {
    private String id;
    private String name;
    private String location;
    private String specialty;
    private LocalDate creationDate;
}