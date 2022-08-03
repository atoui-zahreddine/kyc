package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.ApplicantLevel}.
 */
public interface ApplicantLevelService {
    /**
     * Save a applicantLevel.
     *
     * @param applicantLevelDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantLevelDTO save(ApplicantLevelDTO applicantLevelDTO);

    /**
     * Partially updates a applicantLevel.
     *
     * @param applicantLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantLevelDTO> partialUpdate(ApplicantLevelDTO applicantLevelDTO);

    /**
     * Get all the applicantLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantLevelDTO> findAll(Pageable pageable);

    /**
     * Get all the applicantLevels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantLevelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" applicantLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantLevelDTO> findOne(Long id);

    /**
     * Delete the "id" applicantLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
