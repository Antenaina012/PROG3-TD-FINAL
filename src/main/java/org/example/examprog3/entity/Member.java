package org.example.examprog3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.examprog3.entity.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate enrolmentDate;

    private List<Member> referees;
    private boolean isSuperuser;
}