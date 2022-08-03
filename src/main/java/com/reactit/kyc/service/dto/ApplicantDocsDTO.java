package com.reactit.kyc.service.dto;

import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.ApplicantDocs} entity.
 */
public class ApplicantDocsDTO implements Serializable {

    private Long id;

    private TypeDoc docType;

    private String firstName;

    private String lastName;

    private String number;

    private LocalDate dateOfBirth;

    private LocalDate validUntil;

    private String imageUrl;

    private SubType subTypes;

    private String imageTrust;

    private CountryDTO docsCountry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDoc getDocType() {
        return docType;
    }

    public void setDocType(TypeDoc docType) {
        this.docType = docType;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SubType getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(SubType subTypes) {
        this.subTypes = subTypes;
    }

    public String getImageTrust() {
        return imageTrust;
    }

    public void setImageTrust(String imageTrust) {
        this.imageTrust = imageTrust;
    }

    public CountryDTO getDocsCountry() {
        return docsCountry;
    }

    public void setDocsCountry(CountryDTO docsCountry) {
        this.docsCountry = docsCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantDocsDTO)) {
            return false;
        }

        ApplicantDocsDTO applicantDocsDTO = (ApplicantDocsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicantDocsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantDocsDTO{" +
            "id=" + getId() +
            ", docType='" + getDocType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", number='" + getNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", subTypes='" + getSubTypes() + "'" +
            ", imageTrust='" + getImageTrust() + "'" +
            ", docsCountry=" + getDocsCountry() +
            "}";
    }
}
