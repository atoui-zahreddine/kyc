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
 * Criteria class for the {@link com.reactit.kyc.domain.Step} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.StepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /steps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private InstantFilter createdAt;

    private LongFilter createdBy;

    private InstantFilter modifiedAt;

    private LongFilter docSetId;

    private LongFilter applicantLevelId;

    private Boolean distinct;

    public StepCriteria() {}

    public StepCriteria(StepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedAt = other.modifiedAt == null ? null : other.modifiedAt.copy();
        this.docSetId = other.docSetId == null ? null : other.docSetId.copy();
        this.applicantLevelId = other.applicantLevelId == null ? null : other.applicantLevelId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StepCriteria copy() {
        return new StepCriteria(this);
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

    public LongFilter getDocSetId() {
        return docSetId;
    }

    public LongFilter docSetId() {
        if (docSetId == null) {
            docSetId = new LongFilter();
        }
        return docSetId;
    }

    public void setDocSetId(LongFilter docSetId) {
        this.docSetId = docSetId;
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
        final StepCriteria that = (StepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedAt, that.modifiedAt) &&
            Objects.equals(docSetId, that.docSetId) &&
            Objects.equals(applicantLevelId, that.applicantLevelId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, createdAt, createdBy, modifiedAt, docSetId, applicantLevelId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedAt != null ? "modifiedAt=" + modifiedAt + ", " : "") +
            (docSetId != null ? "docSetId=" + docSetId + ", " : "") +
            (applicantLevelId != null ? "applicantLevelId=" + applicantLevelId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
