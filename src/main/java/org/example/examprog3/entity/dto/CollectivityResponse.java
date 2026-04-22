package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.Structure;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityResponse {
    private String id;
    private String location;
    private Structure structure;
    private List<Member> members;
}