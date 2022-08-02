package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.UserAgentInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.UserAgentInfo}.
 */
public interface UserAgentInfoService {
    /**
     * Save a userAgentInfo.
     *
     * @param userAgentInfoDTO the entity to save.
     * @return the persisted entity.
     */
    UserAgentInfoDTO save(UserAgentInfoDTO userAgentInfoDTO);

    /**
     * Partially updates a userAgentInfo.
     *
     * @param userAgentInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserAgentInfoDTO> partialUpdate(UserAgentInfoDTO userAgentInfoDTO);

    /**
     * Get all the userAgentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserAgentInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userAgentInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAgentInfoDTO> findOne(Long id);

    /**
     * Delete the "id" userAgentInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
