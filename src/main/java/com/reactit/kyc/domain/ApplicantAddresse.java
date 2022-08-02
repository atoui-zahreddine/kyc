package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicantAddresse.
 */
@Entity
@Table(name = "applicant_addresse")
public class ApplicantAddresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "state")
    private String state;

    @Column(name = "street")
    private String street;

    @Column(name = "sub_street")
    private String subStreet;

    @Column(name = "town")
    private String town;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "applicant", "applicantAddresses", "applicantPhones", "applicantDocs", "countryOfBirths" },
        allowSetters = true
    )
    private ApplicantInfo applicantInfo;

    @OneToMany(mappedBy = "addresses")
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Set<Country> addresseCountries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicantAddresse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public ApplicantAddresse postCode(String postCode) {
        this.setPostCode(postCode);
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return this.state;
    }

    public ApplicantAddresse state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return this.street;
    }

    public ApplicantAddresse street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubStreet() {
        return this.subStreet;
    }

    public ApplicantAddresse subStreet(String subStreet) {
        this.setSubStreet(subStreet);
        return this;
    }

    public void setSubStreet(String subStreet) {
        this.subStreet = subStreet;
    }

    public String getTown() {
        return this.town;
    }

    public ApplicantAddresse town(String town) {
        this.setTown(town);
        return this;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public ApplicantAddresse enabled(Boolean enabled) {
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

    public ApplicantAddresse applicantInfo(ApplicantInfo applicantInfo) {
        this.setApplicantInfo(applicantInfo);
        return this;
    }

    public Set<Country> getAddresseCountries() {
        return this.addresseCountries;
    }

    public void setAddresseCountries(Set<Country> countries) {
        if (this.addresseCountries != null) {
            this.addresseCountries.forEach(i -> i.setAddresses(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setAddresses(this));
        }
        this.addresseCountries = countries;
    }

    public ApplicantAddresse addresseCountries(Set<Country> countries) {
        this.setAddresseCountries(countries);
        return this;
    }

    public ApplicantAddresse addAddresseCountry(Country country) {
        this.addresseCountries.add(country);
        country.setAddresses(this);
        return this;
    }

    public ApplicantAddresse removeAddresseCountry(Country country) {
        this.addresseCountries.remove(country);
        country.setAddresses(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantAddresse)) {
            return false;
        }
        return id != null && id.equals(((ApplicantAddresse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantAddresse{" +
            "id=" + getId() +
            ", postCode='" + getPostCode() + "'" +
            ", state='" + getState() + "'" +
            ", street='" + getStreet() + "'" +
            ", subStreet='" + getSubStreet() + "'" +
            ", town='" + getTown() + "'" +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
