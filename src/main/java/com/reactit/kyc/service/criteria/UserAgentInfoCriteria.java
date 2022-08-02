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
 * Criteria class for the {@link com.reactit.kyc.domain.UserAgentInfo} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.UserAgentInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-agent-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserAgentInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uaBrowser;

    private StringFilter uaBrowserVersion;

    private StringFilter uaDeviceType;

    private StringFilter uaPlatform;

    private LongFilter applicantId;

    private Boolean distinct;

    public UserAgentInfoCriteria() {}

    public UserAgentInfoCriteria(UserAgentInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uaBrowser = other.uaBrowser == null ? null : other.uaBrowser.copy();
        this.uaBrowserVersion = other.uaBrowserVersion == null ? null : other.uaBrowserVersion.copy();
        this.uaDeviceType = other.uaDeviceType == null ? null : other.uaDeviceType.copy();
        this.uaPlatform = other.uaPlatform == null ? null : other.uaPlatform.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserAgentInfoCriteria copy() {
        return new UserAgentInfoCriteria(this);
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

    public StringFilter getUaBrowser() {
        return uaBrowser;
    }

    public StringFilter uaBrowser() {
        if (uaBrowser == null) {
            uaBrowser = new StringFilter();
        }
        return uaBrowser;
    }

    public void setUaBrowser(StringFilter uaBrowser) {
        this.uaBrowser = uaBrowser;
    }

    public StringFilter getUaBrowserVersion() {
        return uaBrowserVersion;
    }

    public StringFilter uaBrowserVersion() {
        if (uaBrowserVersion == null) {
            uaBrowserVersion = new StringFilter();
        }
        return uaBrowserVersion;
    }

    public void setUaBrowserVersion(StringFilter uaBrowserVersion) {
        this.uaBrowserVersion = uaBrowserVersion;
    }

    public StringFilter getUaDeviceType() {
        return uaDeviceType;
    }

    public StringFilter uaDeviceType() {
        if (uaDeviceType == null) {
            uaDeviceType = new StringFilter();
        }
        return uaDeviceType;
    }

    public void setUaDeviceType(StringFilter uaDeviceType) {
        this.uaDeviceType = uaDeviceType;
    }

    public StringFilter getUaPlatform() {
        return uaPlatform;
    }

    public StringFilter uaPlatform() {
        if (uaPlatform == null) {
            uaPlatform = new StringFilter();
        }
        return uaPlatform;
    }

    public void setUaPlatform(StringFilter uaPlatform) {
        this.uaPlatform = uaPlatform;
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
        final UserAgentInfoCriteria that = (UserAgentInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uaBrowser, that.uaBrowser) &&
            Objects.equals(uaBrowserVersion, that.uaBrowserVersion) &&
            Objects.equals(uaDeviceType, that.uaDeviceType) &&
            Objects.equals(uaPlatform, that.uaPlatform) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uaBrowser, uaBrowserVersion, uaDeviceType, uaPlatform, applicantId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAgentInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uaBrowser != null ? "uaBrowser=" + uaBrowser + ", " : "") +
            (uaBrowserVersion != null ? "uaBrowserVersion=" + uaBrowserVersion + ", " : "") +
            (uaDeviceType != null ? "uaDeviceType=" + uaDeviceType + ", " : "") +
            (uaPlatform != null ? "uaPlatform=" + uaPlatform + ", " : "") +
            (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
