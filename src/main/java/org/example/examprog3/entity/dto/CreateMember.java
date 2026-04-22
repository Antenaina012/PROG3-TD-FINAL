package org.example.examprog3.entity.dto;

import org.example.examprog3.entity.enums.Gender;
import org.example.examprog3.entity.enums.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

public record CreateMember(
        String firstName,
        String lastName,
        LocalDate birthDate,
        Gender gender,
        String address,
        String profession,
        String phoneNumber,
        String email,
        MemberOccupation occupation,
        String collectivityId,
        List<String> refereeIds,
        boolean registrationFeePaid,
        boolean membershipDuesPaid
) {}