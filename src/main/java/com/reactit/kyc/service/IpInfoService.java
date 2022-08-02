package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.IpInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.IpInfo}.
 */
public interface IpInfoService {
    /**
     * Save a ipInfo.
     *
     * @param ipInfoDTO the entity to save.
     * @return the persisted entity.
     */
    IpInfoDTO save(IpInfoDTO ipInfoDTO);

    /**
     * Partially updates a ipInfo.
     *
     * @param ipInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IpInfoDTO> partialUpdate(IpInfoDTO ipInfoDTO);

    /**
     * Get all the ipInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IpInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ipInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IpInfoDTO> findOne(Long id);

    /**
     * Delete the "id" ipInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
