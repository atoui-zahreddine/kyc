package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A UserAgentInfo.
 */
@Entity
@Table(name = "user_agent_info")
public class UserAgentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ua_browser")
    private String uaBrowser;

    @Column(name = "ua_browser_version")
    private String uaBrowserVersion;

    @Column(name = "ua_device_type")
    private String uaDeviceType;

    @Column(name = "ua_platform")
    private String uaPlatform;

    @JsonIgnoreProperties(value = { "applicantInfo", "ipInfo", "userAgentInfo", "applicantLevels" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAgentInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUaBrowser() {
        return this.uaBrowser;
    }

    public UserAgentInfo uaBrowser(String uaBrowser) {
        this.setUaBrowser(uaBrowser);
        return this;
    }

    public void setUaBrowser(String uaBrowser) {
        this.uaBrowser = uaBrowser;
    }

    public String getUaBrowserVersion() {
        return this.uaBrowserVersion;
    }

    public UserAgentInfo uaBrowserVersion(String uaBrowserVersion) {
        this.setUaBrowserVersion(uaBrowserVersion);
        return this;
    }

    public void setUaBrowserVersion(String uaBrowserVersion) {
        this.uaBrowserVersion = uaBrowserVersion;
    }

    public String getUaDeviceType() {
        return this.uaDeviceType;
    }

    public UserAgentInfo uaDeviceType(String uaDeviceType) {
        this.setUaDeviceType(uaDeviceType);
        return this;
    }

    public void setUaDeviceType(String uaDeviceType) {
        this.uaDeviceType = uaDeviceType;
    }

    public String getUaPlatform() {
        return this.uaPlatform;
    }

    public UserAgentInfo uaPlatform(String uaPlatform) {
        this.setUaPlatform(uaPlatform);
        return this;
    }

    public void setUaPlatform(String uaPlatform) {
        this.uaPlatform = uaPlatform;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public UserAgentInfo applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAgentInfo)) {
            return false;
        }
        return id != null && id.equals(((UserAgentInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAgentInfo{" +
            "id=" + getId() +
            ", uaBrowser='" + getUaBrowser() + "'" +
            ", uaBrowserVersion='" + getUaBrowserVersion() + "'" +
            ", uaDeviceType='" + getUaDeviceType() + "'" +
            ", uaPlatform='" + getUaPlatform() + "'" +
            "}";
    }
}
