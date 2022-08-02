package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicantDocs.
 */
@Entity
@Table(name = "applicant_docs")
public class ApplicantDocs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private TypeDoc docType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number")
    private String number;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_types")
    private SubType subTypes;

    @Column(name = "image_trust")
    private String imageTrust;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "applicant", "applicantAddresses", "applicantPhones", "applicantDocs", "countryOfBirths" },
        allowSetters = true
    )
    private ApplicantInfo applicantInfo;

    @OneToMany(mappedBy = "docs")
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Set<Country> docsCountries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicantDocs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDoc getDocType() {
        return this.docType;
    }

    public ApplicantDocs docType(TypeDoc docType) {
        this.setDocType(docType);
        return this;
    }

    public void setDocType(TypeDoc docType) {
        this.docType = docType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ApplicantDocs firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ApplicantDocs lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return this.number;
    }

    public ApplicantDocs number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public ApplicantDocs dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getValidUntil() {
        return this.validUntil;
    }

    public ApplicantDocs validUntil(LocalDate validUntil) {
        this.setValidUntil(validUntil);
        return this;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public ApplicantDocs imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SubType getSubTypes() {
        return this.subTypes;
    }

    public ApplicantDocs subTypes(SubType subTypes) {
        this.setSubTypes(subTypes);
        return this;
    }

    public void setSubTypes(SubType subTypes) {
        this.subTypes = subTypes;
    }

    public String getImageTrust() {
        return this.imageTrust;
    }

    public ApplicantDocs imageTrust(String imageTrust) {
        this.setImageTrust(imageTrust);
        return this;
    }

    public void setImageTrust(String imageTrust) {
        this.imageTrust = imageTrust;
    }

    public ApplicantInfo getApplicantInfo() {
        return this.applicantInfo;
    }

    public void setApplicantInfo(ApplicantInfo applicantInfo) {
        this.applicantInfo = applicantInfo;
    }

    public ApplicantDocs applicantInfo(ApplicantInfo applicantInfo) {
        this.setApplicantInfo(applicantInfo);
        return this;
    }

    public Set<Country> getDocsCountries() {
        return this.docsCountries;
    }

    public void setDocsCountries(Set<Country> countries) {
        if (this.docsCountries != null) {
            this.docsCountries.forEach(i -> i.setDocs(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setDocs(this));
        }
        this.docsCountries = countries;
    }

    public ApplicantDocs docsCountries(Set<Country> countries) {
        this.setDocsCountries(countries);
        return this;
    }

    public ApplicantDocs addDocsCountry(Country country) {
        this.docsCountries.add(country);
        country.setDocs(this);
        return this;
    }

    public ApplicantDocs removeDocsCountry(Country country) {
        this.docsCountries.remove(country);
        country.setDocs(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantDocs)) {
            return false;
        }
        return id != null && id.equals(((ApplicantDocs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantDocs{" +
            "id=" + getId() +
            ", docType='" + getDocType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", number='" + getNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", subTypes='" + getSubTypes() + "'" +
            ", imageTrust='" + getImageTrust() + "'" +
            "}";
    }
}
