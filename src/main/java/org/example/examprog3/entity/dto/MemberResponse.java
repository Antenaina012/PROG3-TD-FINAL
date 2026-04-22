package org.example.examprog3.entity.dto;

import lombok.Builder;
import lombok.Data;
import org.example.examprog3.entity.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MemberResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;

    private List<Integer> referees;
}
