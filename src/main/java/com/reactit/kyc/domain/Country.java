package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.CountryRegion;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country_code_2")
    private String countryCode2;

    @Column(name = "country_code_3")
    private String countryCode3;

    @Column(name = "phone_code")
    private String phoneCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private CountryRegion region;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicantInfo", "addresseCountries" }, allowSetters = true)
    private ApplicantAddresse addresses;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicantInfo", "docsCountries" }, allowSetters = true)
    private ApplicantDocs docs;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "applicant", "applicantAddresses", "applicantPhones", "applicantDocs", "countryOfBirths" },
        allowSetters = true
    )
    private ApplicantInfo applicants;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicantInfo", "phoneCountries" }, allowSetters = true)
    private ApplicantPhone phones;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Country id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode2() {
        return this.countryCode2;
    }

    public Country countryCode2(String countryCode2) {
        this.setCountryCode2(countryCode2);
        return this;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public String getCountryCode3() {
        return this.countryCode3;
    }

    public Country countryCode3(String countryCode3) {
        this.setCountryCode3(countryCode3);
        return this;
    }

    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public String getPhoneCode() {
        return this.phoneCode;
    }

    public Country phoneCode(String phoneCode) {
        this.setPhoneCode(phoneCode);
        return this;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public CountryRegion getRegion() {
        return this.region;
    }

    public Country region(CountryRegion region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(CountryRegion region) {
        this.region = region;
    }

    public ApplicantAddresse getAddresses() {
        return this.addresses;
    }

    public void setAddresses(ApplicantAddresse applicantAddresse) {
        this.addresses = applicantAddresse;
    }

    public Country addresses(ApplicantAddresse applicantAddresse) {
        this.setAddresses(applicantAddresse);
        return this;
    }

    public ApplicantDocs getDocs() {
        return this.docs;
    }

    public void setDocs(ApplicantDocs applicantDocs) {
        this.docs = applicantDocs;
    }

    public Country docs(ApplicantDocs applicantDocs) {
        this.setDocs(applicantDocs);
        return this;
    }

    public ApplicantInfo getApplicants() {
        return this.applicants;
    }

    public void setApplicants(ApplicantInfo applicantInfo) {
        this.applicants = applicantInfo;
    }

    public Country applicants(ApplicantInfo applicantInfo) {
        this.setApplicants(applicantInfo);
        return this;
    }

    public ApplicantPhone getPhones() {
        return this.phones;
    }

    public void setPhones(ApplicantPhone applicantPhone) {
        this.phones = applicantPhone;
    }

    public Country phones(ApplicantPhone applicantPhone) {
        this.setPhones(applicantPhone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", countryCode2='" + getCountryCode2() + "'" +
            ", countryCode3='" + getCountryCode3() + "'" +
            ", phoneCode='" + getPhoneCode() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
