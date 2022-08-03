package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicantLevel.
 */
@Entity
@Table(name = "applicant_level")
public class ApplicantLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @ManyToMany
    @JoinTable(
        name = "rel_applicant_level__step",
        joinColumns = @JoinColumn(name = "applicant_level_id"),
        inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    @JsonIgnoreProperties(value = { "docSets", "applicantLevels" }, allowSetters = true)
    private Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy = "applicantLevel")
    @JsonIgnoreProperties(value = { "applicantLevel", "applicantInfo", "ipInfo", "userAgentInfo" }, allowSetters = true)
    private Set<Applicant> applicants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicantLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ApplicantLevel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public ApplicantLevel levelName(String levelName) {
        this.setLevelName(levelName);
        return this;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDescription() {
        return this.description;
    }

    public ApplicantLevel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public ApplicantLevel url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ApplicantLevel createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApplicantLevel createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return this.modifiedAt;
    }

    public ApplicantLevel modifiedAt(Instant modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public ApplicantLevel steps(Set<Step> steps) {
        this.setSteps(steps);
        return this;
    }

    public ApplicantLevel addStep(Step step) {
        this.steps.add(step);
        step.getApplicantLevels().add(this);
        return this;
    }

    public ApplicantLevel removeStep(Step step) {
        this.steps.remove(step);
        step.getApplicantLevels().remove(this);
        return this;
    }

    public Set<Applicant> getApplicants() {
        return this.applicants;
    }

    public void setApplicants(Set<Applicant> applicants) {
        if (this.applicants != null) {
            this.applicants.forEach(i -> i.setApplicantLevel(null));
        }
        if (applicants != null) {
            applicants.forEach(i -> i.setApplicantLevel(this));
        }
        this.applicants = applicants;
    }

    public ApplicantLevel applicants(Set<Applicant> applicants) {
        this.setApplicants(applicants);
        return this;
    }

    public ApplicantLevel addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
        applicant.setApplicantLevel(this);
        return this;
    }

    public ApplicantLevel removeApplicant(Applicant applicant) {
        this.applicants.remove(applicant);
        applicant.setApplicantLevel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantLevel)) {
            return false;
        }
        return id != null && id.equals(((ApplicantLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantLevel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", levelName='" + getLevelName() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
