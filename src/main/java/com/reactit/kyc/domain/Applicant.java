package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.Platform;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Applicant.
 */
@Entity
@Table(name = "applicant")
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private Platform platform;

    @JsonIgnoreProperties(
        value = { "applicant", "applicantAddresses", "applicantPhones", "applicantDocs", "countryOfBirths" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "applicant")
    private ApplicantInfo applicantInfo;

    @JsonIgnoreProperties(value = { "applicant" }, allowSetters = true)
    @OneToOne(mappedBy = "applicant")
    private IpInfo ipInfo;

    @JsonIgnoreProperties(value = { "applicant" }, allowSetters = true)
    @OneToOne(mappedBy = "applicant")
    private UserAgentInfo userAgentInfo;

    @OneToMany(mappedBy = "applicant")
    @JsonIgnoreProperties(value = { "applicant", "steps" }, allowSetters = true)
    private Set<ApplicantLevel> applicantLevels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Applicant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Applicant createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Applicant createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return this.modifiedAt;
    }

    public Applicant modifiedAt(Instant modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public Applicant platform(Platform platform) {
        this.setPlatform(platform);
        return this;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public ApplicantInfo getApplicantInfo() {
        return this.applicantInfo;
    }

    public void setApplicantInfo(ApplicantInfo applicantInfo) {
        if (this.applicantInfo != null) {
            this.applicantInfo.setApplicant(null);
        }
        if (applicantInfo != null) {
            applicantInfo.setApplicant(this);
        }
        this.applicantInfo = applicantInfo;
    }

    public Applicant applicantInfo(ApplicantInfo applicantInfo) {
        this.setApplicantInfo(applicantInfo);
        return this;
    }

    public IpInfo getIpInfo() {
        return this.ipInfo;
    }

    public void setIpInfo(IpInfo ipInfo) {
        if (this.ipInfo != null) {
            this.ipInfo.setApplicant(null);
        }
        if (ipInfo != null) {
            ipInfo.setApplicant(this);
        }
        this.ipInfo = ipInfo;
    }

    public Applicant ipInfo(IpInfo ipInfo) {
        this.setIpInfo(ipInfo);
        return this;
    }

    public UserAgentInfo getUserAgentInfo() {
        return this.userAgentInfo;
    }

    public void setUserAgentInfo(UserAgentInfo userAgentInfo) {
        if (this.userAgentInfo != null) {
            this.userAgentInfo.setApplicant(null);
        }
        if (userAgentInfo != null) {
            userAgentInfo.setApplicant(this);
        }
        this.userAgentInfo = userAgentInfo;
    }

    public Applicant userAgentInfo(UserAgentInfo userAgentInfo) {
        this.setUserAgentInfo(userAgentInfo);
        return this;
    }

    public Set<ApplicantLevel> getApplicantLevels() {
        return this.applicantLevels;
    }

    public void setApplicantLevels(Set<ApplicantLevel> applicantLevels) {
        if (this.applicantLevels != null) {
            this.applicantLevels.forEach(i -> i.setApplicant(null));
        }
        if (applicantLevels != null) {
            applicantLevels.forEach(i -> i.setApplicant(this));
        }
        this.applicantLevels = applicantLevels;
    }

    public Applicant applicantLevels(Set<ApplicantLevel> applicantLevels) {
        this.setApplicantLevels(applicantLevels);
        return this;
    }

    public Applicant addApplicantLevel(ApplicantLevel applicantLevel) {
        this.applicantLevels.add(applicantLevel);
        applicantLevel.setApplicant(this);
        return this;
    }

    public Applicant removeApplicantLevel(ApplicantLevel applicantLevel) {
        this.applicantLevels.remove(applicantLevel);
        applicantLevel.setApplicant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applicant)) {
            return false;
        }
        return id != null && id.equals(((Applicant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applicant{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", platform='" + getPlatform() + "'" +
            "}";
    }
}
