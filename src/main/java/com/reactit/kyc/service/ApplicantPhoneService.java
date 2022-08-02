package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.ApplicantPhone}.
 */
public interface ApplicantPhoneService {
    /**
     * Save a applicantPhone.
     *
     * @param applicantPhoneDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantPhoneDTO save(ApplicantPhoneDTO applicantPhoneDTO);

    /**
     * Partially updates a applicantPhone.
     *
     * @param applicantPhoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantPhoneDTO> partialUpdate(ApplicantPhoneDTO applicantPhoneDTO);

    /**
     * Get all the applicantPhones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantPhoneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicantPhone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantPhoneDTO> findOne(Long id);

    /**
     * Delete the "id" applicantPhone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
