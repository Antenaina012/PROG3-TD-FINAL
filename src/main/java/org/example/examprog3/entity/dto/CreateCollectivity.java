package org.example.examprog3.entity.dto;

import java.util.List;

public record CreateCollectivity(
        String name,
        String location,
        String specialty,
        List<String> memberIds,
        boolean federationApproval,
        CreateCollectivityStructure structure
) {}