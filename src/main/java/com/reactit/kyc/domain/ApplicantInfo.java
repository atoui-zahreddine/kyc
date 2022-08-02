package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicantInfo.
 */
@Entity
@Table(name = "applicant_info")
public class ApplicantInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "addresses")
    private String addresses;

    @Column(name = "email")
    private String email;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "state_of_birth")
    private String stateOfBirth;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @JsonIgnoreProperties(value = { "applicantInfo", "ipInfo", "userAgentInfo", "applicantLevels" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Applicant applicant;

    @OneToMany(mappedBy = "applicantInfo")
    @JsonIgnoreProperties(value = { "applicantInfo", "addresseCountries" }, allowSetters = true)
    private Set<ApplicantAddresse> applicantAddresses = new HashSet<>();

    @OneToMany(mappedBy = "applicantInfo")
    @JsonIgnoreProperties(value = { "applicantInfo", "phoneCountries" }, allowSetters = true)
    private Set<ApplicantPhone> applicantPhones = new HashSet<>();

    @OneToMany(mappedBy = "applicantInfo")
    @JsonIgnoreProperties(value = { "applicantInfo", "docsCountries" }, allowSetters = true)
    private Set<ApplicantDocs> applicantDocs = new HashSet<>();

    @OneToMany(mappedBy = "applicants")
    @JsonIgnoreProperties(value = { "addresses", "docs", "applicants", "phones" }, allowSetters = true)
    private Set<Country> countryOfBirths = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicantInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ApplicantInfo firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ApplicantInfo lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddresses() {
        return this.addresses;
    }

    public ApplicantInfo addresses(String addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getEmail() {
        return this.email;
    }

    public ApplicantInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public ApplicantInfo middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStateOfBirth() {
        return this.stateOfBirth;
    }

    public ApplicantInfo stateOfBirth(String stateOfBirth) {
        this.setStateOfBirth(stateOfBirth);
        return this;
    }

    public void setStateOfBirth(String stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public ApplicantInfo dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public ApplicantInfo placeOfBirth(String placeOfBirth) {
        this.setPlaceOfBirth(placeOfBirth);
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNationality() {
        return this.nationality;
    }

    public ApplicantInfo nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return this.gender;
    }

    public ApplicantInfo gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public ApplicantInfo applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    public Set<ApplicantAddresse> getApplicantAddresses() {
        return this.applicantAddresses;
    }

    public void setApplicantAddresses(Set<ApplicantAddresse> applicantAddresses) {
        if (this.applicantAddresses != null) {
            this.applicantAddresses.forEach(i -> i.setApplicantInfo(null));
        }
        if (applicantAddresses != null) {
            applicantAddresses.forEach(i -> i.setApplicantInfo(this));
        }
        this.applicantAddresses = applicantAddresses;
    }

    public ApplicantInfo applicantAddresses(Set<ApplicantAddresse> applicantAddresses) {
        this.setApplicantAddresses(applicantAddresses);
        return this;
    }

    public ApplicantInfo addApplicantAddresse(ApplicantAddresse applicantAddresse) {
        this.applicantAddresses.add(applicantAddresse);
        applicantAddresse.setApplicantInfo(this);
        return this;
    }

    public ApplicantInfo removeApplicantAddresse(ApplicantAddresse applicantAddresse) {
        this.applicantAddresses.remove(applicantAddresse);
        applicantAddresse.setApplicantInfo(null);
        return this;
    }

    public Set<ApplicantPhone> getApplicantPhones() {
        return this.applicantPhones;
    }

    public void setApplicantPhones(Set<ApplicantPhone> applicantPhones) {
        if (this.applicantPhones != null) {
            this.applicantPhones.forEach(i -> i.setApplicantInfo(null));
        }
        if (applicantPhones != null) {
            applicantPhones.forEach(i -> i.setApplicantInfo(this));
        }
        this.applicantPhones = applicantPhones;
    }

    public ApplicantInfo applicantPhones(Set<ApplicantPhone> applicantPhones) {
        this.setApplicantPhones(applicantPhones);
        return this;
    }

    public ApplicantInfo addApplicantPhone(ApplicantPhone applicantPhone) {
        this.applicantPhones.add(applicantPhone);
        applicantPhone.setApplicantInfo(this);
        return this;
    }

    public ApplicantInfo removeApplicantPhone(ApplicantPhone applicantPhone) {
        this.applicantPhones.remove(applicantPhone);
        applicantPhone.setApplicantInfo(null);
        return this;
    }

    public Set<ApplicantDocs> getApplicantDocs() {
        return this.applicantDocs;
    }

    public void setApplicantDocs(Set<ApplicantDocs> applicantDocs) {
        if (this.applicantDocs != null) {
            this.applicantDocs.forEach(i -> i.setApplicantInfo(null));
        }
        if (applicantDocs != null) {
            applicantDocs.forEach(i -> i.setApplicantInfo(this));
        }
        this.applicantDocs = applicantDocs;
    }

    public ApplicantInfo applicantDocs(Set<ApplicantDocs> applicantDocs) {
        this.setApplicantDocs(applicantDocs);
        return this;
    }

    public ApplicantInfo addApplicantDocs(ApplicantDocs applicantDocs) {
        this.applicantDocs.add(applicantDocs);
        applicantDocs.setApplicantInfo(this);
        return this;
    }

    public ApplicantInfo removeApplicantDocs(ApplicantDocs applicantDocs) {
        this.applicantDocs.remove(applicantDocs);
        applicantDocs.setApplicantInfo(null);
        return this;
    }

    public Set<Country> getCountryOfBirths() {
        return this.countryOfBirths;
    }

    public void setCountryOfBirths(Set<Country> countries) {
        if (this.countryOfBirths != null) {
            this.countryOfBirths.forEach(i -> i.setApplicants(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setApplicants(this));
        }
        this.countryOfBirths = countries;
    }

    public ApplicantInfo countryOfBirths(Set<Country> countries) {
        this.setCountryOfBirths(countries);
        return this;
    }

    public ApplicantInfo addCountryOfBirth(Country country) {
        this.countryOfBirths.add(country);
        country.setApplicants(this);
        return this;
    }

    public ApplicantInfo removeCountryOfBirth(Country country) {
        this.countryOfBirths.remove(country);
        country.setApplicants(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantInfo)) {
            return false;
        }
        return id != null && id.equals(((ApplicantInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantInfo{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", addresses='" + getAddresses() + "'" +
            ", email='" + getEmail() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", stateOfBirth='" + getStateOfBirth() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}