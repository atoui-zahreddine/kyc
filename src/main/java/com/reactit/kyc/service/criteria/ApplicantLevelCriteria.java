package com.reactit.kyc.service.criteria;

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
 * Criteria class for the {@link com.reactit.kyc.domain.ApplicantLevel} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.ApplicantLevelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-levels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantLevelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter levelName;

    private StringFilter description;

    private StringFilter url;

    private InstantFilter createdAt;

    private LongFilter createdBy;

    private InstantFilter modifiedAt;

    private LongFilter stepId;

    private LongFilter applicantId;

    private Boolean distinct;

    public ApplicantLevelCriteria() {}

    public ApplicantLevelCriteria(ApplicantLevelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.levelName = other.levelName == null ? null : other.levelName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedAt = other.modifiedAt == null ? null : other.modifiedAt.copy();
        this.stepId = other.stepId == null ? null : other.stepId.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicantLevelCriteria copy() {
        return new ApplicantLevelCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getLevelName() {
        return levelName;
    }

    public StringFilter levelName() {
        if (levelName == null) {
            levelName = new StringFilter();
        }
        return levelName;
    }

    public void setLevelName(StringFilter levelName) {
        this.levelName = levelName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getUrl() {
        return url;
    }

    public StringFilter url() {
        if (url == null) {
            url = new StringFilter();
        }
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
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

    public LongFilter getStepId() {
        return stepId;
    }

    public LongFilter stepId() {
        if (stepId == null) {
            stepId = new LongFilter();
        }
        return stepId;
    }

    public void setStepId(LongFilter stepId) {
        this.stepId = stepId;
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
        final ApplicantLevelCriteria that = (ApplicantLevelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(levelName, that.levelName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(url, that.url) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedAt, that.modifiedAt) &&
            Objects.equals(stepId, that.stepId) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, levelName, description, url, createdAt, createdBy, modifiedAt, stepId, applicantId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantLevelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (levelName != null ? "levelName=" + levelName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (url != null ? "url=" + url + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedAt != null ? "modifiedAt=" + modifiedAt + ", " : "") +
            (stepId != null ? "stepId=" + stepId + ", " : "") +
            (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
