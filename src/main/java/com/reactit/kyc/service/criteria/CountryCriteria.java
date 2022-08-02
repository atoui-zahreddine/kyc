package com.reactit.kyc.service.criteria;

import com.reactit.kyc.domain.enumeration.CountryRegion;
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
 * Criteria class for the {@link com.reactit.kyc.domain.Country} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CountryRegion
     */
    public static class CountryRegionFilter extends Filter<CountryRegion> {

        public CountryRegionFilter() {}

        public CountryRegionFilter(CountryRegionFilter filter) {
            super(filter);
        }

        @Override
        public CountryRegionFilter copy() {
            return new CountryRegionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter countryCode2;

    private StringFilter countryCode3;

    private StringFilter phoneCode;

    private CountryRegionFilter region;

    private LongFilter addressesId;

    private LongFilter docsId;

    private LongFilter applicantsId;

    private LongFilter phonesId;

    private Boolean distinct;

    public CountryCriteria() {}

    public CountryCriteria(CountryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.countryCode2 = other.countryCode2 == null ? null : other.countryCode2.copy();
        this.countryCode3 = other.countryCode3 == null ? null : other.countryCode3.copy();
        this.phoneCode = other.phoneCode == null ? null : other.phoneCode.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.addressesId = other.addressesId == null ? null : other.addressesId.copy();
        this.docsId = other.docsId == null ? null : other.docsId.copy();
        this.applicantsId = other.applicantsId == null ? null : other.applicantsId.copy();
        this.phonesId = other.phonesId == null ? null : other.phonesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCountryCode2() {
        return countryCode2;
    }

    public StringFilter countryCode2() {
        if (countryCode2 == null) {
            countryCode2 = new StringFilter();
        }
        return countryCode2;
    }

    public void setCountryCode2(StringFilter countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public StringFilter getCountryCode3() {
        return countryCode3;
    }

    public StringFilter countryCode3() {
        if (countryCode3 == null) {
            countryCode3 = new StringFilter();
        }
        return countryCode3;
    }

    public void setCountryCode3(StringFilter countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public StringFilter getPhoneCode() {
        return phoneCode;
    }

    public StringFilter phoneCode() {
        if (phoneCode == null) {
            phoneCode = new StringFilter();
        }
        return phoneCode;
    }

    public void setPhoneCode(StringFilter phoneCode) {
        this.phoneCode = phoneCode;
    }

    public CountryRegionFilter getRegion() {
        return region;
    }

    public CountryRegionFilter region() {
        if (region == null) {
            region = new CountryRegionFilter();
        }
        return region;
    }

    public void setRegion(CountryRegionFilter region) {
        this.region = region;
    }

    public LongFilter getAddressesId() {
        return addressesId;
    }

    public LongFilter addressesId() {
        if (addressesId == null) {
            addressesId = new LongFilter();
        }
        return addressesId;
    }

    public void setAddressesId(LongFilter addressesId) {
        this.addressesId = addressesId;
    }

    public LongFilter getDocsId() {
        return docsId;
    }

    public LongFilter docsId() {
        if (docsId == null) {
            docsId = new LongFilter();
        }
        return docsId;
    }

    public void setDocsId(LongFilter docsId) {
        this.docsId = docsId;
    }

    public LongFilter getApplicantsId() {
        return applicantsId;
    }

    public LongFilter applicantsId() {
        if (applicantsId == null) {
            applicantsId = new LongFilter();
        }
        return applicantsId;
    }

    public void setApplicantsId(LongFilter applicantsId) {
        this.applicantsId = applicantsId;
    }

    public LongFilter getPhonesId() {
        return phonesId;
    }

    public LongFilter phonesId() {
        if (phonesId == null) {
            phonesId = new LongFilter();
        }
        return phonesId;
    }

    public void setPhonesId(LongFilter phonesId) {
        this.phonesId = phonesId;
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
        final CountryCriteria that = (CountryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(countryCode2, that.countryCode2) &&
            Objects.equals(countryCode3, that.countryCode3) &&
            Objects.equals(phoneCode, that.phoneCode) &&
            Objects.equals(region, that.region) &&
            Objects.equals(addressesId, that.addressesId) &&
            Objects.equals(docsId, that.docsId) &&
            Objects.equals(applicantsId, that.applicantsId) &&
            Objects.equals(phonesId, that.phonesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryCode2, countryCode3, phoneCode, region, addressesId, docsId, applicantsId, phonesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (countryCode2 != null ? "countryCode2=" + countryCode2 + ", " : "") +
            (countryCode3 != null ? "countryCode3=" + countryCode3 + ", " : "") +
            (phoneCode != null ? "phoneCode=" + phoneCode + ", " : "") +
            (region != null ? "region=" + region + ", " : "") +
            (addressesId != null ? "addressesId=" + addressesId + ", " : "") +
            (docsId != null ? "docsId=" + docsId + ", " : "") +
            (applicantsId != null ? "applicantsId=" + applicantsId + ", " : "") +
            (phonesId != null ? "phonesId=" + phonesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
