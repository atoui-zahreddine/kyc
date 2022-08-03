package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.CountryRegion;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "addresseCountry")
    @JsonIgnoreProperties(value = { "addresseCountry", "applicantInfos" }, allowSetters = true)
    private Set<ApplicantAddresse> addresses = new HashSet<>();

    @OneToMany(mappedBy = "docsCountry")
    @JsonIgnoreProperties(value = { "docsCountry", "applicantInfos" }, allowSetters = true)
    private Set<ApplicantDocs> docs = new HashSet<>();

    @OneToMany(mappedBy = "countryOfBirth")
    @JsonIgnoreProperties(
        value = { "applicant", "countryOfBirth", "applicantAddresses", "applicantPhones", "applicantDocs" },
        allowSetters = true
    )
    private Set<ApplicantInfo> applicants = new HashSet<>();

    @OneToMany(mappedBy = "phoneCountry")
    @JsonIgnoreProperties(value = { "phoneCountry", "applicantInfos" }, allowSetters = true)
    private Set<ApplicantPhone> phones = new HashSet<>();

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

    public Set<ApplicantAddresse> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<ApplicantAddresse> applicantAddresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setAddresseCountry(null));
        }
        if (applicantAddresses != null) {
            applicantAddresses.forEach(i -> i.setAddresseCountry(this));
        }
        this.addresses = applicantAddresses;
    }

    public Country addresses(Set<ApplicantAddresse> applicantAddresses) {
        this.setAddresses(applicantAddresses);
        return this;
    }

    public Country addAddresses(ApplicantAddresse applicantAddresse) {
        this.addresses.add(applicantAddresse);
        applicantAddresse.setAddresseCountry(this);
        return this;
    }

    public Country removeAddresses(ApplicantAddresse applicantAddresse) {
        this.addresses.remove(applicantAddresse);
        applicantAddresse.setAddresseCountry(null);
        return this;
    }

    public Set<ApplicantDocs> getDocs() {
        return this.docs;
    }

    public void setDocs(Set<ApplicantDocs> applicantDocs) {
        if (this.docs != null) {
            this.docs.forEach(i -> i.setDocsCountry(null));
        }
        if (applicantDocs != null) {
            applicantDocs.forEach(i -> i.setDocsCountry(this));
        }
        this.docs = applicantDocs;
    }

    public Country docs(Set<ApplicantDocs> applicantDocs) {
        this.setDocs(applicantDocs);
        return this;
    }

    public Country addDocs(ApplicantDocs applicantDocs) {
        this.docs.add(applicantDocs);
        applicantDocs.setDocsCountry(this);
        return this;
    }

    public Country removeDocs(ApplicantDocs applicantDocs) {
        this.docs.remove(applicantDocs);
        applicantDocs.setDocsCountry(null);
        return this;
    }

    public Set<ApplicantInfo> getApplicants() {
        return this.applicants;
    }

    public void setApplicants(Set<ApplicantInfo> applicantInfos) {
        if (this.applicants != null) {
            this.applicants.forEach(i -> i.setCountryOfBirth(null));
        }
        if (applicantInfos != null) {
            applicantInfos.forEach(i -> i.setCountryOfBirth(this));
        }
        this.applicants = applicantInfos;
    }

    public Country applicants(Set<ApplicantInfo> applicantInfos) {
        this.setApplicants(applicantInfos);
        return this;
    }

    public Country addApplicants(ApplicantInfo applicantInfo) {
        this.applicants.add(applicantInfo);
        applicantInfo.setCountryOfBirth(this);
        return this;
    }

    public Country removeApplicants(ApplicantInfo applicantInfo) {
        this.applicants.remove(applicantInfo);
        applicantInfo.setCountryOfBirth(null);
        return this;
    }

    public Set<ApplicantPhone> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<ApplicantPhone> applicantPhones) {
        if (this.phones != null) {
            this.phones.forEach(i -> i.setPhoneCountry(null));
        }
        if (applicantPhones != null) {
            applicantPhones.forEach(i -> i.setPhoneCountry(this));
        }
        this.phones = applicantPhones;
    }

    public Country phones(Set<ApplicantPhone> applicantPhones) {
        this.setPhones(applicantPhones);
        return this;
    }

    public Country addPhones(ApplicantPhone applicantPhone) {
        this.phones.add(applicantPhone);
        applicantPhone.setPhoneCountry(this);
        return this;
    }

    public Country removePhones(ApplicantPhone applicantPhone) {
        this.phones.remove(applicantPhone);
        applicantPhone.setPhoneCountry(null);
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
