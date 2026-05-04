package org.example.examprog3.entity.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.example.examprog3.entity.enums.CollectivityOccupation;
import org.example.examprog3.entity.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateMember {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private java.time.Instant enrolmentDate;
    private Gender gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;

    private CollectivityOccupation occupation;

    private String collectivityIdentifier;
    private List<String> referees;
    
    // Selon section B-2: nature de la relation avec chaque parrain (famille, amis, collègues, etc.)
    private Map<String, String> refereeRelations;

    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;
}