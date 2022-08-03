package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.reactit.kyc.domain.Step} entity.
 */
public class StepDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Instant createdAt;

    private Long createdBy;

    private Instant modifiedAt;

    private Set<DocSetDTO> docSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<DocSetDTO> getDocSets() {
        return docSets;
    }

    public void setDocSets(Set<DocSetDTO> docSets) {
        this.docSets = docSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StepDTO)) {
            return false;
        }

        StepDTO stepDTO = (StepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", docSets=" + getDocSets() +
            "}";
    }
}
