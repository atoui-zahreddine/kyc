package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Step.
 */
@Entity
@Table(name = "step")
public class Step implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @ManyToMany
    @JoinTable(
        name = "rel_step__doc_set",
        joinColumns = @JoinColumn(name = "step_id"),
        inverseJoinColumns = @JoinColumn(name = "doc_set_id")
    )
    @JsonIgnoreProperties(value = { "steps" }, allowSetters = true)
    private Set<DocSet> docSets = new HashSet<>();

    @ManyToMany(mappedBy = "steps")
    @JsonIgnoreProperties(value = { "steps", "applicants" }, allowSetters = true)
    private Set<ApplicantLevel> applicantLevels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Step id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Step code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Step name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Step description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Step createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Step createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return this.modifiedAt;
    }

    public Step modifiedAt(Instant modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<DocSet> getDocSets() {
        return this.docSets;
    }

    public void setDocSets(Set<DocSet> docSets) {
        this.docSets = docSets;
    }

    public Step docSets(Set<DocSet> docSets) {
        this.setDocSets(docSets);
        return this;
    }

    public Step addDocSet(DocSet docSet) {
        this.docSets.add(docSet);
        docSet.getSteps().add(this);
        return this;
    }

    public Step removeDocSet(DocSet docSet) {
        this.docSets.remove(docSet);
        docSet.getSteps().remove(this);
        return this;
    }

    public Set<ApplicantLevel> getApplicantLevels() {
        return this.applicantLevels;
    }

    public void setApplicantLevels(Set<ApplicantLevel> applicantLevels) {
        if (this.applicantLevels != null) {
            this.applicantLevels.forEach(i -> i.removeStep(this));
        }
        if (applicantLevels != null) {
            applicantLevels.forEach(i -> i.addStep(this));
        }
        this.applicantLevels = applicantLevels;
    }

    public Step applicantLevels(Set<ApplicantLevel> applicantLevels) {
        this.setApplicantLevels(applicantLevels);
        return this;
    }

    public Step addApplicantLevel(ApplicantLevel applicantLevel) {
        this.applicantLevels.add(applicantLevel);
        applicantLevel.getSteps().add(this);
        return this;
    }

    public Step removeApplicantLevel(ApplicantLevel applicantLevel) {
        this.applicantLevels.remove(applicantLevel);
        applicantLevel.getSteps().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Step)) {
            return false;
        }
        return id != null && id.equals(((Step) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Step{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
