package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.DocSetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.kyc.domain.DocSet}.
 */
public interface DocSetService {
    /**
     * Save a docSet.
     *
     * @param docSetDTO the entity to save.
     * @return the persisted entity.
     */
    DocSetDTO save(DocSetDTO docSetDTO);

    /**
     * Partially updates a docSet.
     *
     * @param docSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocSetDTO> partialUpdate(DocSetDTO docSetDTO);

    /**
     * Get all the docSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocSetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" docSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocSetDTO> findOne(Long id);

    /**
     * Delete the "id" docSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
