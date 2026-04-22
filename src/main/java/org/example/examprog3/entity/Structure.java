package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Structure {
    private String id;
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;
}