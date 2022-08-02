package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.ApplicantDocs}.
 */
public interface ApplicantDocsService {
    /**
     * Save a applicantDocs.
     *
     * @param applicantDocsDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantDocsDTO save(ApplicantDocsDTO applicantDocsDTO);

    /**
     * Partially updates a applicantDocs.
     *
     * @param applicantDocsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantDocsDTO> partialUpdate(ApplicantDocsDTO applicantDocsDTO);

    /**
     * Get all the applicantDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantDocsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicantDocs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantDocsDTO> findOne(Long id);

    /**
     * Delete the "id" applicantDocs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
