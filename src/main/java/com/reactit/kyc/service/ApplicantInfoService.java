package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.ApplicantInfo}.
 */
public interface ApplicantInfoService {
    /**
     * Save a applicantInfo.
     *
     * @param applicantInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantInfoDTO save(ApplicantInfoDTO applicantInfoDTO);

    /**
     * Partially updates a applicantInfo.
     *
     * @param applicantInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantInfoDTO> partialUpdate(ApplicantInfoDTO applicantInfoDTO);

    /**
     * Get all the applicantInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the applicantInfos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantInfoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" applicantInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantInfoDTO> findOne(Long id);

    /**
     * Delete the "id" applicantInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
