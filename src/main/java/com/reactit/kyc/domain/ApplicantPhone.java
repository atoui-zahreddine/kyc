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
    @JsonIgnoreProperties(
        value = { "applicant", "applicantAddresses", "applicantPhones", "applicantDocs", "countryOfBirths" },
        allowSetters = true
    )
    private ApplicantInfo applicantInfo;

    @OneToMany(mappedBy = "phones")
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Set<Country> phoneCountries = new HashSet<>();

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

    public ApplicantInfo getApplicantInfo() {
        return this.applicantInfo;
    }

    public void setApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfo = applicantInfo;
    }

    public ApplicantPhone applicantInfo(ApplicantInfo applicantInfo) {
        this.setApplicantInfo(applicantInfo);
        return this;
    }

    public Set<Country> getPhoneCountries() {
        return this.phoneCountries;
    }

    public void setPhoneCountries(Set<Country> countries) {
        if (this.phoneCountries != null) {
            this.phoneCountries.forEach(i -> i.setPhones(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setPhones(this));
        }
        this.phoneCountries = countries;
    }

    public ApplicantPhone phoneCountries(Set<Country> countries) {
        this.setPhoneCountries(countries);
        return this;
    }

    public ApplicantPhone addPhoneCountry(Country country) {
        this.phoneCountries.add(country);
        country.setPhones(this);
        return this;
    }

    public ApplicantPhone removePhoneCountry(Country country) {
        this.phoneCountries.remove(country);
        country.setPhones(null);
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
