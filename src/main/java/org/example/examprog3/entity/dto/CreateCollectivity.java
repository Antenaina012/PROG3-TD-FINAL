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
    private String id;         // AJOUTÉ : pour "col-1", "col-2"
    private String number;     // AJOUTÉ : pour "1", "2"
    private String name;       // AJOUTÉ : pour "Mpanorina"
    private String speciality; // AJOUTÉ : pour "Riziculture"
    private String location;
    private List<String> members;
    private boolean federationApproval;
    private CreateStructure structure;
}
