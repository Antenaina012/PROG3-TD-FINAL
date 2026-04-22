package org.example.examprog3.validator;


import org.example.examprog3.entity.Member;
import org.example.examprog3.exeption.SponsorTenureException;
import org.springframework.stereotype.Component;

@Component
public class SponsorTenureValidator {
    public void validate(Member sponsor){
        if(!sponsor.isAValidSponsor()){
            throw new SponsorTenureException(sponsor.getId() + "'s tenure in Federation is below 90 days");
        }
    }
}
