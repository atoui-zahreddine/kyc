package com.reactit.kyc.service.criteria;

import com.reactit.kyc.domain.enumeration.Platform;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.reactit.kyc.domain.Applicant} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Platform
     */
    public static class PlatformFilter extends Filter<Platform> {

        public PlatformFilter() {}

        public PlatformFilter(PlatformFilter filter) {
            super(filter);
        }

        @Override
        public PlatformFilter copy() {
            return new PlatformFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private LongFilter createdBy;

    private InstantFilter modifiedAt;

    private PlatformFilter platform;

    private LongFilter applicantInfoId;

    private LongFilter ipInfoId;

    private LongFilter userAgentInfoId;

    private LongFilter applicantLevelId;

    private Boolean distinct;

    public ApplicantCriteria() {}

    public ApplicantCriteria(ApplicantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedAt = other.modifiedAt == null ? null : other.modifiedAt.copy();
        this.platform = other.platform == null ? null : other.platform.copy();
        this.applicantInfoId = other.applicantInfoId == null ? null : other.applicantInfoId.copy();
        this.ipInfoId = other.ipInfoId == null ? null : other.ipInfoId.copy();
        this.userAgentInfoId = other.userAgentInfoId == null ? null : other.userAgentInfoId.copy();
        this.applicantLevelId = other.applicantLevelId == null ? null : other.applicantLevelId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantCriteria copy() {
        return new ApplicantCriteria(this);
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

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public LongFilter getCreatedBy() {
        return createdBy;
    }

    public LongFilter createdBy() {
        if (createdBy == null) {
            createdBy = new LongFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(LongFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getModifiedAt() {
        return modifiedAt;
    }

    public InstantFilter modifiedAt() {
        if (modifiedAt == null) {
            modifiedAt = new InstantFilter();
        }
        return modifiedAt;
    }

    public void setModifiedAt(InstantFilter modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public PlatformFilter getPlatform() {
        return platform;
    }

    public PlatformFilter platform() {
        if (platform == null) {
            platform = new PlatformFilter();
        }
        return platform;
    }

    public void setPlatform(PlatformFilter platform) {
        this.platform = platform;
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

    public LongFilter getIpInfoId() {
        return ipInfoId;
    }

    public LongFilter ipInfoId() {
        if (ipInfoId == null) {
            ipInfoId = new LongFilter();
        }
        return ipInfoId;
    }

    public void setIpInfoId(LongFilter ipInfoId) {
        this.ipInfoId = ipInfoId;
    }

    public LongFilter getUserAgentInfoId() {
        return userAgentInfoId;
    }

    public LongFilter userAgentInfoId() {
        if (userAgentInfoId == null) {
            userAgentInfoId = new LongFilter();
        }
        return userAgentInfoId;
    }

    public void setUserAgentInfoId(LongFilter userAgentInfoId) {
        this.userAgentInfoId = userAgentInfoId;
    }

    public LongFilter getApplicantLevelId() {
        return applicantLevelId;
    }

    public LongFilter applicantLevelId() {
        if (applicantLevelId == null) {
            applicantLevelId = new LongFilter();
        }
        return applicantLevelId;
    }

    public void setApplicantLevelId(LongFilter applicantLevelId) {
        this.applicantLevelId = applicantLevelId;
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
        final ApplicantCriteria that = (ApplicantCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedAt, that.modifiedAt) &&
            Objects.equals(platform, that.platform) &&
            Objects.equals(applicantInfoId, that.applicantInfoId) &&
            Objects.equals(ipInfoId, that.ipInfoId) &&
            Objects.equals(userAgentInfoId, that.userAgentInfoId) &&
            Objects.equals(applicantLevelId, that.applicantLevelId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdAt,
            createdBy,
            modifiedAt,
            platform,
            applicantInfoId,
            ipInfoId,
            userAgentInfoId,
            applicantLevelId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedAt != null ? "modifiedAt=" + modifiedAt + ", " : "") +
            (platform != null ? "platform=" + platform + ", " : "") +
            (applicantInfoId != null ? "applicantInfoId=" + applicantInfoId + ", " : "") +
            (ipInfoId != null ? "ipInfoId=" + ipInfoId + ", " : "") +
            (userAgentInfoId != null ? "userAgentInfoId=" + userAgentInfoId + ", " : "") +
            (applicantLevelId != null ? "applicantLevelId=" + applicantLevelId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
