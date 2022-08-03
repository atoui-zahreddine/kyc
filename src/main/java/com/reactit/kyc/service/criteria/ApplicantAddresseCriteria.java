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
 * Criteria class for the {@link com.reactit.kyc.domain.ApplicantAddresse} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantAddresseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantAddresseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter postCode;

    private StringFilter state;

    private StringFilter street;

    private StringFilter subStreet;

    private StringFilter town;

    private BooleanFilter enabled;

    private LongFilter addresseCountryId;

    private LongFilter applicantInfoId;

    private Boolean distinct;

    public ApplicantAddresseCriteria() {}

    public ApplicantAddresseCriteria(ApplicantAddresseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.subStreet = other.subStreet == null ? null : other.subStreet.copy();
        this.town = other.town == null ? null : other.town.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.addresseCountryId = other.addresseCountryId == null ? null : other.addresseCountryId.copy();
        this.applicantInfoId = other.applicantInfoId == null ? null : other.applicantInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantAddresseCriteria copy() {
        return new ApplicantAddresseCriteria(this);
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

    public StringFilter getPostCode() {
        return postCode;
    }

    public StringFilter postCode() {
        if (postCode == null) {
            postCode = new StringFilter();
        }
        return postCode;
    }

    public void setPostCode(StringFilter postCode) {
        this.postCode = postCode;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getStreet() {
        return street;
    }

    public StringFilter street() {
        if (street == null) {
            street = new StringFilter();
        }
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getSubStreet() {
        return subStreet;
    }

    public StringFilter subStreet() {
        if (subStreet == null) {
            subStreet = new StringFilter();
        }
        return subStreet;
    }

    public void setSubStreet(StringFilter subStreet) {
        this.subStreet = subStreet;
    }

    public StringFilter getTown() {
        return town;
    }

    public StringFilter town() {
        if (town == null) {
            town = new StringFilter();
        }
        return town;
    }

    public void setTown(StringFilter town) {
        this.town = town;
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

    public LongFilter getAddresseCountryId() {
        return addresseCountryId;
    }

    public LongFilter addresseCountryId() {
        if (addresseCountryId == null) {
            addresseCountryId = new LongFilter();
        }
        return addresseCountryId;
    }

    public void setAddresseCountryId(LongFilter addresseCountryId) {
        this.addresseCountryId = addresseCountryId;
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
        final ApplicantAddresseCriteria that = (ApplicantAddresseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(state, that.state) &&
            Objects.equals(street, that.street) &&
            Objects.equals(subStreet, that.subStreet) &&
            Objects.equals(town, that.town) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(addresseCountryId, that.addresseCountryId) &&
            Objects.equals(applicantInfoId, that.applicantInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postCode, state, street, subStreet, town, enabled, addresseCountryId, applicantInfoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantAddresseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (postCode != null ? "postCode=" + postCode + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (street != null ? "street=" + street + ", " : "") +
            (subStreet != null ? "subStreet=" + subStreet + ", " : "") +
            (town != null ? "town=" + town + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (addresseCountryId != null ? "addresseCountryId=" + addresseCountryId + ", " : "") +
            (applicantInfoId != null ? "applicantInfoId=" + applicantInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
