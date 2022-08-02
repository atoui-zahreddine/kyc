package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.ApplicantAddresse} entity.
 */
public class ApplicantAddresseDTO implements Serializable {

    private Long id;

    private String postCode;

    private String state;

    private String street;

    private String subStreet;

    private String town;

    private Boolean enabled;

    private ApplicantInfoDTO applicantInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubStreet() {
        return subStreet;
    }

    public void setSubStreet(String subStreet) {
        this.subStreet = subStreet;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ApplicantInfoDTO getApplicantInfo() {
        return applicantInfo;
    }

    public void setApplicantInfo(ApplicantInfoDTO applicantInfo) {
        this.applicantInfo = applicantInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantAddresseDTO)) {
            return false;
        }

        ApplicantAddresseDTO applicantAddresseDTO = (ApplicantAddresseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicantAddresseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantAddresseDTO{" +
            "id=" + getId() +
            ", postCode='" + getPostCode() + "'" +
            ", state='" + getState() + "'" +
            ", street='" + getStreet() + "'" +
            ", subStreet='" + getSubStreet() + "'" +
            ", town='" + getTown() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", applicantInfo=" + getApplicantInfo() +
            "}";
    }
}
