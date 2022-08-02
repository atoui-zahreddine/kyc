package com.reactit.kyc.service.criteria;

import com.reactit.kyc.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.reactit.kyc.domain.ApplicantInfo} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantInfoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter addresses;

    private StringFilter email;

    private StringFilter middleName;

    private StringFilter stateOfBirth;

    private LocalDateFilter dateOfBirth;

    private StringFilter placeOfBirth;

    private StringFilter nationality;

    private GenderFilter gender;

    private LongFilter applicantId;

    private LongFilter applicantAddresseId;

    private LongFilter applicantPhoneId;

    private LongFilter applicantDocsId;

    private LongFilter countryOfBirthId;

    private Boolean distinct;

    public ApplicantInfoCriteria() {}

    public ApplicantInfoCriteria(ApplicantInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.addresses = other.addresses == null ? null : other.addresses.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.stateOfBirth = other.stateOfBirth == null ? null : other.stateOfBirth.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.placeOfBirth = other.placeOfBirth == null ? null : other.placeOfBirth.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.applicantAddresseId = other.applicantAddresseId == null ? null : other.applicantAddresseId.copy();
        this.applicantPhoneId = other.applicantPhoneId == null ? null : other.applicantPhoneId.copy();
        this.applicantDocsId = other.applicantDocsId == null ? null : other.applicantDocsId.copy();
        this.countryOfBirthId = other.countryOfBirthId == null ? null : other.countryOfBirthId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantInfoCriteria copy() {
        return new ApplicantInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getAddresses() {
        return addresses;
    }

    public StringFilter addresses() {
        if (addresses == null) {
            addresses = new StringFilter();
        }
        return addresses;
    }

    public void setAddresses(StringFilter addresses) {
        this.addresses = addresses;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getStateOfBirth() {
        return stateOfBirth;
    }

    public StringFilter stateOfBirth() {
        if (stateOfBirth == null) {
            stateOfBirth = new StringFilter();
        }
        return stateOfBirth;
    }

    public void setStateOfBirth(StringFilter stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new LocalDateFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getPlaceOfBirth() {
        return placeOfBirth;
    }

    public StringFilter placeOfBirth() {
        if (placeOfBirth == null) {
            placeOfBirth = new StringFilter();
        }
        return placeOfBirth;
    }

    public void setPlaceOfBirth(StringFilter placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public LongFilter applicantId() {
        if (applicantId == null) {
            applicantId = new LongFilter();
        }
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }

    public LongFilter getApplicantAddresseId() {
        return applicantAddresseId;
    }

    public LongFilter applicantAddresseId() {
        if (applicantAddresseId == null) {
            applicantAddresseId = new LongFilter();
        }
        return applicantAddresseId;
    }

    public void setApplicantAddresseId(LongFilter applicantAddresseId) {
        this.applicantAddresseId = applicantAddresseId;
    }

    public LongFilter getApplicantPhoneId() {
        return applicantPhoneId;
    }

    public LongFilter applicantPhoneId() {
        if (applicantPhoneId == null) {
            applicantPhoneId = new LongFilter();
        }
        return applicantPhoneId;
    }

    public void setApplicantPhoneId(LongFilter applicantPhoneId) {
        this.applicantPhoneId = applicantPhoneId;
    }

    public LongFilter getApplicantDocsId() {
        return applicantDocsId;
    }

    public LongFilter applicantDocsId() {
        if (applicantDocsId == null) {
            applicantDocsId = new LongFilter();
        }
        return applicantDocsId;
    }

    public void setApplicantDocsId(LongFilter applicantDocsId) {
        this.applicantDocsId = applicantDocsId;
    }

    public LongFilter getCountryOfBirthId() {
        return countryOfBirthId;
    }

    public LongFilter countryOfBirthId() {
        if (countryOfBirthId == null) {
            countryOfBirthId = new LongFilter();
        }
        return countryOfBirthId;
    }

    public void setCountryOfBirthId(LongFilter countryOfBirthId) {
        this.countryOfBirthId = countryOfBirthId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicantInfoCriteria that = (ApplicantInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(addresses, that.addresses) &&
            Objects.equals(email, that.email) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(stateOfBirth, that.stateOfBirth) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(placeOfBirth, that.placeOfBirth) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(applicantAddresseId, that.applicantAddresseId) &&
            Objects.equals(applicantPhoneId, that.applicantPhoneId) &&
            Objects.equals(applicantDocsId, that.applicantDocsId) &&
            Objects.equals(countryOfBirthId, that.countryOfBirthId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            addresses,
            email,
            middleName,
            stateOfBirth,
            dateOfBirth,
            placeOfBirth,
            nationality,
            gender,
            applicantId,
            applicantAddresseId,
            applicantPhoneId,
            applicantDocsId,
            countryOfBirthId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (addresses != null ? "addresses=" + addresses + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (stateOfBirth != null ? "stateOfBirth=" + stateOfBirth + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            (applicantAddresseId != null ? "applicantAddresseId=" + applicantAddresseId + ", " : "") +
            (applicantPhoneId != null ? "applicantPhoneId=" + applicantPhoneId + ", " : "") +
            (applicantDocsId != null ? "applicantDocsId=" + applicantDocsId + ", " : "") +
            (countryOfBirthId != null ? "countryOfBirthId=" + countryOfBirthId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
