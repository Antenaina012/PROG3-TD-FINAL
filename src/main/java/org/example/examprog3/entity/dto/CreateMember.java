package org.example.examprog3.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.CollectivityOccupation;
import org.example.examprog3.entity.enums.Gender;

import java.time.LocalDate;
import java.util.List;


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

    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;
}