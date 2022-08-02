package com.reactit.kyc.service.dto;

import com.reactit.kyc.domain.enumeration.CountryRegion;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.Country} entity.
 */
public class CountryDTO implements Serializable {

    private Long id;

    private String name;

    private String countryCode2;

    private String countryCode3;

    private String phoneCode;

    private CountryRegion region;

    private ApplicantAddresseDTO addresses;

    private ApplicantDocsDTO docs;

    private ApplicantInfoDTO applicants;

    private ApplicantPhoneDTO phones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public String getCountryCode3() {
        return countryCode3;
    }

    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public CountryRegion getRegion() {
        return region;
    }

    public void setRegion(CountryRegion region) {
        this.region = region;
    }

    public ApplicantAddresseDTO getAddresses() {
        return addresses;
    }

    public void setAddresses(ApplicantAddresseDTO addresses) {
        this.addresses = addresses;
    }

    public ApplicantDocsDTO getDocs() {
        return docs;
    }

    public void setDocs(ApplicantDocsDTO docs) {
        this.docs = docs;
    }

    public ApplicantInfoDTO getApplicants() {
        return applicants;
    }

    public void setApplicants(ApplicantInfoDTO applicants) {
        this.applicants = applicants;
    }

    public ApplicantPhoneDTO getPhones() {
        return phones;
    }

    public void setPhones(ApplicantPhoneDTO phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", countryCode2='" + getCountryCode2() + "'" +
            ", countryCode3='" + getCountryCode3() + "'" +
            ", phoneCode='" + getPhoneCode() + "'" +
            ", region='" + getRegion() + "'" +
            ", addresses=" + getAddresses() +
            ", docs=" + getDocs() +
            ", applicants=" + getApplicants() +
            ", phones=" + getPhones() +
            "}";
    }
}
