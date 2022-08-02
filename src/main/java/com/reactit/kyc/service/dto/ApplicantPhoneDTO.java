package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.ApplicantPhone} entity.
 */
public class ApplicantPhoneDTO implements Serializable {

    private Long id;

    private String country;

    private String number;

    private Boolean enabled;

    private ApplicantInfoDTO applicantInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if (!(o instanceof ApplicantPhoneDTO)) {
            return false;
        }

        ApplicantPhoneDTO applicantPhoneDTO = (ApplicantPhoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicantPhoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantPhoneDTO{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", number='" + getNumber() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", applicantInfo=" + getApplicantInfo() +
            "}";
    }
}
