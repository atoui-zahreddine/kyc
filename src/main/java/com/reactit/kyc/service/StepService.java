package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.StepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.Step}.
 */
public interface StepService {
    /**
     * Save a step.
     *
     * @param stepDTO the entity to save.
     * @return the persisted entity.
     */
    StepDTO save(StepDTO stepDTO);

    /**
     * Partially updates a step.
     *
     * @param stepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StepDTO> partialUpdate(StepDTO stepDTO);

    /**
     * Get all the steps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepDTO> findAll(Pageable pageable);

    /**
     * Get all the steps with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" step.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StepDTO> findOne(Long id);

    /**
     * Delete the "id" step.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
