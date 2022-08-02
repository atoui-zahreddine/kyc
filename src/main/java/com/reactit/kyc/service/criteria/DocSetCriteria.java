package com.reactit.kyc.service.criteria;

import com.reactit.kyc.domain.enumeration.IdDocSetType;
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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.reactit.kyc.domain.DocSet} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.DocSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doc-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocSetCriteria implements Serializable, Criteria {

    /**
     * Class for filtering IdDocSetType
     */
    public static class IdDocSetTypeFilter extends Filter<IdDocSetType> {

        public IdDocSetTypeFilter() {}

        public IdDocSetTypeFilter(IdDocSetTypeFilter filter) {
            super(filter);
        }

        @Override
        public IdDocSetTypeFilter copy() {
            return new IdDocSetTypeFilter(this);
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IdDocSetTypeFilter idDocSetType;

    private SubTypeFilter subTypes;

    private TypeDocFilter types;

    private LongFilter stepId;

    private Boolean distinct;

    public DocSetCriteria() {}

    public DocSetCriteria(DocSetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idDocSetType = other.idDocSetType == null ? null : other.idDocSetType.copy();
        this.subTypes = other.subTypes == null ? null : other.subTypes.copy();
        this.types = other.types == null ? null : other.types.copy();
        this.stepId = other.stepId == null ? null : other.stepId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocSetCriteria copy() {
        return new DocSetCriteria(this);
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

    public IdDocSetTypeFilter getIdDocSetType() {
        return idDocSetType;
    }

    public IdDocSetTypeFilter idDocSetType() {
        if (idDocSetType == null) {
            idDocSetType = new IdDocSetTypeFilter();
        }
        return idDocSetType;
    }

    public void setIdDocSetType(IdDocSetTypeFilter idDocSetType) {
        this.idDocSetType = idDocSetType;
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

    public TypeDocFilter getTypes() {
        return types;
    }

    public TypeDocFilter types() {
        if (types == null) {
            types = new TypeDocFilter();
        }
        return types;
    }

    public void setTypes(TypeDocFilter types) {
        this.types = types;
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
        final DocSetCriteria that = (DocSetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idDocSetType, that.idDocSetType) &&
            Objects.equals(subTypes, that.subTypes) &&
            Objects.equals(types, that.types) &&
            Objects.equals(stepId, that.stepId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDocSetType, subTypes, types, stepId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocSetCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idDocSetType != null ? "idDocSetType=" + idDocSetType + ", " : "") +
            (subTypes != null ? "subTypes=" + subTypes + ", " : "") +
            (types != null ? "types=" + types + ", " : "") +
            (stepId != null ? "stepId=" + stepId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
