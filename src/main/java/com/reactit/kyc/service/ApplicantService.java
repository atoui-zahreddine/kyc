package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.ApplicantDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.Applicant}.
 */
public interface ApplicantService {
    /**
     * Save a applicant.
     *
     * @param applicantDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicantDTO save(ApplicantDTO applicantDTO);

    /**
     * Partially updates a applicant.
     *
     * @param applicantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicantDTO> partialUpdate(ApplicantDTO applicantDTO);

    /**
     * Get all the applicants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicantDTO> findAll(Pageable pageable);
    /**
     * Get all the ApplicantDTO where ApplicantInfo is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApplicantDTO> findAllWhereApplicantInfoIsNull();
    /**
     * Get all the ApplicantDTO where IpInfo is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApplicantDTO> findAllWhereIpInfoIsNull();
    /**
     * Get all the ApplicantDTO where UserAgentInfo is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApplicantDTO> findAllWhereUserAgentInfoIsNull();

    /**
     * Get the "id" applicant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicantDTO> findOne(Long id);

    /**
     * Delete the "id" applicant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
