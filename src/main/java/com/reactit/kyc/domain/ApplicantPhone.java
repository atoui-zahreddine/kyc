package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicantPhone.
 */
@Entity
@Table(name = "applicant_phone")
public class ApplicantPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "number")
    private String number;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Country phoneCountry;

    @ManyToMany(mappedBy = "applicantPhones")
    @JsonIgnoreProperties(
        value = { "applicant", "countryOfBirth", "applicantAddresses", "applicantPhones", "applicantDocs" },
        allowSetters = true
    )
    private Set<ApplicantInfo> applicantInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicantPhone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return this.country;
    }

    public ApplicantPhone country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return this.number;
    }

    public ApplicantPhone number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public ApplicantPhone enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Country getPhoneCountry() {
        return this.phoneCountry;
    }

    public void setPhoneCountry(Country country) {
        this.phoneCountry = country;
    }

    public ApplicantPhone phoneCountry(Country country) {
        this.setPhoneCountry(country);
        return this;
    }

    public Set<ApplicantInfo> getApplicantInfos() {
        return this.applicantInfos;
    }

    public void setApplicantInfos(Set<ApplicantInfo> applicantInfos) {
        if (this.applicantInfos != null) {
            this.applicantInfos.forEach(i -> i.removeApplicantPhone(this));
        }
        if (applicantInfos != null) {
            applicantInfos.forEach(i -> i.addApplicantPhone(this));
        }
        this.applicantInfos = applicantInfos;
    }

    public ApplicantPhone applicantInfos(Set<ApplicantInfo> applicantInfos) {
        this.setApplicantInfos(applicantInfos);
        return this;
    }

    public ApplicantPhone addApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfos.add(applicantInfo);
        applicantInfo.getApplicantPhones().add(this);
        return this;
    }

    public ApplicantPhone removeApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfos.remove(applicantInfo);
        applicantInfo.getApplicantPhones().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantPhone)) {
            return false;
        }
        return id != null && id.equals(((ApplicantPhone) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantPhone{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", number='" + getNumber() + "'" +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
