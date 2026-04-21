package org.example.examprog3.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private GenderEnum gender; // MALE / FEMALE
    private String address;
    private String profession;
    private int phoneNumber;
    private String email;
    private OccupationEnum occupation; // JUNIOR, SENIOR, PRESIDENT, etc.
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;
    private List<Member> referees;

    public Member() {
    }

    public Member(String id, String firstName, String lastName, LocalDate birthDate,
                  GenderEnum gender, String address, String profession, int phoneNumber,
                  String email, OccupationEnum occupation, boolean registrationFeePaid,
                  boolean membershipDuesPaid, List<Member> referees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.registrationFeePaid = registrationFeePaid;
        this.membershipDuesPaid = membershipDuesPaid;
        this.referees = referees;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public GenderEnum getGender() { return gender; }
    public void setGender(GenderEnum gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public OccupationEnum getOccupation() { return occupation; }
    public void setOccupation(OccupationEnum occupation) { this.occupation = occupation; }

    public boolean isRegistrationFeePaid() { return registrationFeePaid; }
    public void setRegistrationFeePaid(boolean registrationFeePaid) { this.registrationFeePaid = registrationFeePaid; }

    public boolean isMembershipDuesPaid() { return membershipDuesPaid; }
    public void setMembershipDuesPaid(boolean membershipDuesPaid) { this.membershipDuesPaid = membershipDuesPaid; }

    public List<Member> getReferees() { return referees; }
    public void setReferees(List<Member> referees) { this.referees = referees; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
