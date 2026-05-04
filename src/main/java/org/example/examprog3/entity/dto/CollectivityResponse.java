package org.example.examprog3.entity.dto;

import java.util.List;

import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.Structure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityResponse {
    private String id;
    private String name;
    private String number;
    private String location;
    private Structure structure;
    private List<Member> members;
}