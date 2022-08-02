package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.UserAgentInfo} entity.
 */
public class UserAgentInfoDTO implements Serializable {

    private Long id;

    private String uaBrowser;

    private String uaBrowserVersion;

    private String uaDeviceType;

    private String uaPlatform;

    private ApplicantDTO applicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUaBrowser() {
        return uaBrowser;
    }

    public void setUaBrowser(String uaBrowser) {
        this.uaBrowser = uaBrowser;
    }

    public String getUaBrowserVersion() {
        return uaBrowserVersion;
    }

    public void setUaBrowserVersion(String uaBrowserVersion) {
        this.uaBrowserVersion = uaBrowserVersion;
    }

    public String getUaDeviceType() {
        return uaDeviceType;
    }

    public void setUaDeviceType(String uaDeviceType) {
        this.uaDeviceType = uaDeviceType;
    }

    public String getUaPlatform() {
        return uaPlatform;
    }

    public void setUaPlatform(String uaPlatform) {
        this.uaPlatform = uaPlatform;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAgentInfoDTO)) {
            return false;
        }

        UserAgentInfoDTO userAgentInfoDTO = (UserAgentInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAgentInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAgentInfoDTO{" +
            "id=" + getId() +
            ", uaBrowser='" + getUaBrowser() + "'" +
            ", uaBrowserVersion='" + getUaBrowserVersion() + "'" +
            ", uaDeviceType='" + getUaDeviceType() + "'" +
            ", uaPlatform='" + getUaPlatform() + "'" +
            ", applicant=" + getApplicant() +
            "}";
    }
}
