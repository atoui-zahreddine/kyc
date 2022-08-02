package com.reactit.kyc.service.dto;

import com.reactit.kyc.domain.enumeration.IdDocSetType;
import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.DocSet} entity.
 */
public class DocSetDTO implements Serializable {

    private Long id;

    private IdDocSetType idDocSetType;

    private SubType subTypes;

    private TypeDoc types;

    private StepDTO step;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdDocSetType getIdDocSetType() {
        return idDocSetType;
    }

    public void setIdDocSetType(IdDocSetType idDocSetType) {
        this.idDocSetType = idDocSetType;
    }

    public SubType getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(SubType subTypes) {
        this.subTypes = subTypes;
    }

    public TypeDoc getTypes() {
        return types;
    }

    public void setTypes(TypeDoc types) {
        this.types = types;
    }

    public StepDTO getStep() {
        return step;
    }

    public void setStep(StepDTO step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocSetDTO)) {
            return false;
        }

        DocSetDTO docSetDTO = (DocSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, docSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocSetDTO{" +
            "id=" + getId() +
            ", idDocSetType='" + getIdDocSetType() + "'" +
            ", subTypes='" + getSubTypes() + "'" +
            ", types='" + getTypes() + "'" +
            ", step=" + getStep() +
            "}";
    }
}
