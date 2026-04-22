package org.example.examprog3.entity;

import org.example.examprog3.entity.enums.Gender;
import org.example.examprog3.entity.enums.MemberOccupation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private LocalDate joinDate;
    private Collectivity collectivity; // Relation vers la collectivité
}