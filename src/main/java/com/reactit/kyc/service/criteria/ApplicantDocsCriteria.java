package com.reactit.kyc.service.criteria;

import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
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
 * Criteria class for the {@link com.reactit.kyc.domain.ApplicantDocs} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantDocsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-docs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantDocsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TypeDoc
     */
    public static class TypeDocFilter extends Filter<TypeDoc> {

        public TypeDocFilter() {}

        public TypeDocFilter(TypeDocFilter filter) {
            super(filter);
        }

        @Override
        public TypeDocFilter copy() {
            return new TypeDocFilter(this);
        }
    }

    /**
     * Class for filtering SubType
     */
    public static class SubTypeFilter extends Filter<SubType> {

        public SubTypeFilter() {}

        public SubTypeFilter(SubTypeFilter filter) {
            super(filter);
        }

        @Override
        public SubTypeFilter copy() {
            return new SubTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TypeDocFilter docType;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter number;

    private LocalDateFilter dateOfBirth;

    private LocalDateFilter validUntil;

    private StringFilter imageUrl;

    private SubTypeFilter subTypes;

    private StringFilter imageTrust;

    private LongFilter docsCountryId;

    private LongFilter applicantInfoId;

    private Boolean distinct;

    public ApplicantDocsCriteria() {}

    public ApplicantDocsCriteria(ApplicantDocsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.docType = other.docType == null ? null : other.docType.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.validUntil = other.validUntil == null ? null : other.validUntil.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.subTypes = other.subTypes == null ? null : other.subTypes.copy();
        this.imageTrust = other.imageTrust == null ? null : other.imageTrust.copy();
        this.docsCountryId = other.docsCountryId == null ? null : other.docsCountryId.copy();
        this.applicantInfoId = other.applicantInfoId == null ? null : other.applicantInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantDocsCriteria copy() {
        return new ApplicantDocsCriteria(this);
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

    public TypeDocFilter getDocType() {
        return docType;
    }

    public TypeDocFilter docType() {
        if (docType == null) {
            docType = new TypeDocFilter();
        }
        return docType;
    }

    public void setDocType(TypeDocFilter docType) {
        this.docType = docType;
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

    public LocalDateFilter getValidUntil() {
        return validUntil;
    }

    public LocalDateFilter validUntil() {
        if (validUntil == null) {
            validUntil = new LocalDateFilter();
        }
        return validUntil;
    }

    public void setValidUntil(LocalDateFilter validUntil) {
        this.validUntil = validUntil;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SubTypeFilter getSubTypes() {
        return subTypes;
    }

    public SubTypeFilter subTypes() {
        if (subTypes == null) {
            subTypes = new SubTypeFilter();
        }
        return subTypes;
    }

    public void setSubTypes(SubTypeFilter subTypes) {
        this.subTypes = subTypes;
    }

    public StringFilter getImageTrust() {
        return imageTrust;
    }

    public StringFilter imageTrust() {
        if (imageTrust == null) {
            imageTrust = new StringFilter();
        }
        return imageTrust;
    }

    public void setImageTrust(StringFilter imageTrust) {
        this.imageTrust = imageTrust;
    }

    public LongFilter getDocsCountryId() {
        return docsCountryId;
    }

    public LongFilter docsCountryId() {
        if (docsCountryId == null) {
            docsCountryId = new LongFilter();
        }
        return docsCountryId;
    }

    public void setDocsCountryId(LongFilter docsCountryId) {
        this.docsCountryId = docsCountryId;
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
        final ApplicantDocsCriteria that = (ApplicantDocsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(docType, that.docType) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(number, that.number) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(validUntil, that.validUntil) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(subTypes, that.subTypes) &&
            Objects.equals(imageTrust, that.imageTrust) &&
            Objects.equals(docsCountryId, that.docsCountryId) &&
            Objects.equals(applicantInfoId, that.applicantInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            docType,
            firstName,
            lastName,
            number,
            dateOfBirth,
            validUntil,
            imageUrl,
            subTypes,
            imageTrust,
            docsCountryId,
            applicantInfoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantDocsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (docType != null ? "docType=" + docType + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (validUntil != null ? "validUntil=" + validUntil + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (subTypes != null ? "subTypes=" + subTypes + ", " : "") +
            (imageTrust != null ? "imageTrust=" + imageTrust + ", " : "") +
            (docsCountryId != null ? "docsCountryId=" + docsCountryId + ", " : "") +
            (applicantInfoId != null ? "applicantInfoId=" + applicantInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
