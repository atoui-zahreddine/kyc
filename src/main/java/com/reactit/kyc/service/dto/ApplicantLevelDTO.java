package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.ApplicantLevel} entity.
 */
public class ApplicantLevelDTO implements Serializable {

    private Long id;

    private String code;

    private String levelName;

    private String description;

    private String url;

    private Instant createdAt;

    private Long createdBy;

    private Instant modifiedAt;

    private ApplicantDTO applicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
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
        if (!(o instanceof ApplicantLevelDTO)) {
            return false;
        }

        ApplicantLevelDTO applicantLevelDTO = (ApplicantLevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicantLevelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantLevelDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", levelName='" + getLevelName() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", applicant=" + getApplicant() +
            "}";
    }
}
