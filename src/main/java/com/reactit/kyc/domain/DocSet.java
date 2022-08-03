package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.kyc.domain.enumeration.IdDocSetType;
import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DocSet.
 */
@Entity
@Table(name = "doc_set")
public class DocSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_doc_set_type")
    private IdDocSetType idDocSetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_types")
    private SubType subTypes;

    @Enumerated(EnumType.STRING)
    @Column(name = "types")
    private TypeDoc types;

    @ManyToMany(mappedBy = "docSets")
    @JsonIgnoreProperties(value = { "docSets", "applicantLevels" }, allowSetters = true)
    private Set<Step> steps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdDocSetType getIdDocSetType() {
        return this.idDocSetType;
    }

    public DocSet idDocSetType(IdDocSetType idDocSetType) {
        this.setIdDocSetType(idDocSetType);
        return this;
    }

    public void setIdDocSetType(IdDocSetType idDocSetType) {
        this.idDocSetType = idDocSetType;
    }

    public SubType getSubTypes() {
        return this.subTypes;
    }

    public DocSet subTypes(SubType subTypes) {
        this.setSubTypes(subTypes);
        return this;
    }

    public void setSubTypes(SubType subTypes) {
        this.subTypes = subTypes;
    }

    public TypeDoc getTypes() {
        return this.types;
    }

    public DocSet types(TypeDoc types) {
        this.setTypes(types);
        return this;
    }

    public void setTypes(TypeDoc types) {
        this.types = types;
    }

    public Set<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<Step> steps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.removeDocSet(this));
        }
        if (steps != null) {
            steps.forEach(i -> i.addDocSet(this));
        }
        this.steps = steps;
    }

    public DocSet steps(Set<Step> steps) {
        this.setSteps(steps);
        return this;
    }

    public DocSet addStep(Step step) {
        this.steps.add(step);
        step.getDocSets().add(this);
        return this;
    }

    public DocSet removeStep(Step step) {
        this.steps.remove(step);
        step.getDocSets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocSet)) {
            return false;
        }
        return id != null && id.equals(((DocSet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocSet{" +
            "id=" + getId() +
            ", idDocSetType='" + getIdDocSetType() + "'" +
            ", subTypes='" + getSubTypes() + "'" +
            ", types='" + getTypes() + "'" +
            "}";
    }
}
