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
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Country addresseCountry;

    @ManyToMany(mappedBy = "applicantAddresses")
    @JsonIgnoreProperties(
        value = { "applicant", "countryOfBirth", "applicantAddresses", "applicantPhones", "applicantDocs" },
        allowSetters = true
    )
    private Set<ApplicantInfo> applicantInfos = new HashSet<>();

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

    public Country getAddresseCountry() {
        return this.addresseCountry;
    }

    public void setAddresseCountry(Country country) {
        this.addresseCountry = country;
    }

    public ApplicantAddresse addresseCountry(Country country) {
        this.setAddresseCountry(country);
        return this;
    }

    public Set<ApplicantInfo> getApplicantInfos() {
        return this.applicantInfos;
    }

    public void setApplicantInfos(Set<ApplicantInfo> applicantInfos) {
        if (this.applicantInfos != null) {
            this.applicantInfos.forEach(i -> i.removeApplicantAddresse(this));
        }
        if (applicantInfos != null) {
            applicantInfos.forEach(i -> i.addApplicantAddresse(this));
        }
        this.applicantInfos = applicantInfos;
    }

    public ApplicantAddresse applicantInfos(Set<ApplicantInfo> applicantInfos) {
        this.setApplicantInfos(applicantInfos);
        return this;
    }

    public ApplicantAddresse addApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfos.add(applicantInfo);
        applicantInfo.getApplicantAddresses().add(this);
        return this;
    }

    public ApplicantAddresse removeApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfos.remove(applicantInfo);
        applicantInfo.getApplicantAddresses().remove(this);
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
