package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCollectivity {
    private String location;
    private List<String> memberIds;
    private boolean federationApproval;
    private CreateStructure structure;
}
