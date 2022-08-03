package com.reactit.kyc.service.dto;

import com.reactit.kyc.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.reactit.kyc.domain.ApplicantInfo} entity.
 */
public class ApplicantInfoDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String addresses;

    private String email;

    private String middleName;

    private String stateOfBirth;

    private LocalDate dateOfBirth;

    private String placeOfBirth;

    private String nationality;

    private Gender gender;

    private ApplicantDTO applicant;

    private CountryDTO countryOfBirth;

    private Set<ApplicantAddresseDTO> applicantAddresses = new HashSet<>();

    private Set<ApplicantPhoneDTO> applicantPhones = new HashSet<>();

    private Set<ApplicantDocsDTO> applicantDocs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStateOfBirth() {
        return stateOfBirth;
    }

    public void setStateOfBirth(String stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    public CountryDTO getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(CountryDTO countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public Set<ApplicantAddresseDTO> getApplicantAddresses() {
        return applicantAddresses;
    }

    public void setApplicantAddresses(Set<ApplicantAddresseDTO> applicantAddresses) {
        this.applicantAddresses = applicantAddresses;
    }

    public Set<ApplicantPhoneDTO> getApplicantPhones() {
        return applicantPhones;
    }

    public void setApplicantPhones(Set<ApplicantPhoneDTO> applicantPhones) {
        this.applicantPhones = applicantPhones;
    }

    public Set<ApplicantDocsDTO> getApplicantDocs() {
        return applicantDocs;
    }

    public void setApplicantDocs(Set<ApplicantDocsDTO> applicantDocs) {
        this.applicantDocs = applicantDocs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantInfoDTO)) {
            return false;
        }

        ApplicantInfoDTO applicantInfoDTO = (ApplicantInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicantInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantInfoDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", addresses='" + getAddresses() + "'" +
            ", email='" + getEmail() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", stateOfBirth='" + getStateOfBirth() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", applicant=" + getApplicant() +
            ", countryOfBirth=" + getCountryOfBirth() +
            ", applicantAddresses=" + getApplicantAddresses() +
            ", applicantPhones=" + getApplicantPhones() +
            ", applicantDocs=" + getApplicantDocs() +
            "}";
    }
}
