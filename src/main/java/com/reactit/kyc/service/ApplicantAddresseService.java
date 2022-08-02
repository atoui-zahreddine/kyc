package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.ApplicantAddresse}.
 */
public interface ApplicantAddresseService {
    /**
     * Save a applicantAddresse.
     *
     * @param applicantAddresseDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantAddresseDTO save(ApplicantAddresseDTO applicantAddresseDTO);

    /**
     * Partially updates a applicantAddresse.
     *
     * @param applicantAddresseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantAddresseDTO> partialUpdate(ApplicantAddresseDTO applicantAddresseDTO);

    /**
     * Get all the applicantAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantAddresseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicantAddresse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantAddresseDTO> findOne(Long id);

    /**
     * Delete the "id" applicantAddresse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
