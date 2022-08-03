package com.reactit.kyc.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.reactit.kyc.domain.ApplicantPhone} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantPhoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-phones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantPhoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter country;

    private StringFilter number;

    private BooleanFilter enabled;

    private LongFilter phoneCountryId;

    private LongFilter applicantInfoId;

    private Boolean distinct;

    public ApplicantPhoneCriteria() {}

    public ApplicantPhoneCriteria(ApplicantPhoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.phoneCountryId = other.phoneCountryId == null ? null : other.phoneCountryId.copy();
        this.applicantInfoId = other.applicantInfoId == null ? null : other.applicantInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantPhoneCriteria copy() {
        return new ApplicantPhoneCriteria(this);
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

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getNumber() {
        return number;
    }

    public StringFilter number() {
        if (number == null) {
            number = new StringFilter();
        }
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public BooleanFilter enabled() {
        if (enabled == null) {
            enabled = new BooleanFilter();
        }
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public LongFilter getPhoneCountryId() {
        return phoneCountryId;
    }

    public LongFilter phoneCountryId() {
        if (phoneCountryId == null) {
            phoneCountryId = new LongFilter();
        }
        return phoneCountryId;
    }

    public void setPhoneCountryId(LongFilter phoneCountryId) {
        this.phoneCountryId = phoneCountryId;
    }

    public LongFilter getApplicantInfoId() {
        return applicantInfoId;
    }

    public LongFilter applicantInfoId() {
        if (applicantInfoId == null) {
            applicantInfoId = new LongFilter();
        }
        return applicantInfoId;
    }

    public void setApplicantInfoId(LongFilter applicantInfoId) {
        this.applicantInfoId = applicantInfoId;
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
        final ApplicantPhoneCriteria that = (ApplicantPhoneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(country, that.country) &&
            Objects.equals(number, that.number) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(phoneCountryId, that.phoneCountryId) &&
            Objects.equals(applicantInfoId, that.applicantInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, number, enabled, phoneCountryId, applicantInfoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantPhoneCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (phoneCountryId != null ? "phoneCountryId=" + phoneCountryId + ", " : "") +
            (applicantInfoId != null ? "applicantInfoId=" + applicantInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
