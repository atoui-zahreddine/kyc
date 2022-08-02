package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.repository.DocSetRepository;
import com.reactit.kyc.service.criteria.DocSetCriteria;
import com.reactit.kyc.service.dto.DocSetDTO;
import com.reactit.kyc.service.mapper.DocSetMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DocSet} entities in the database.
 * The main input is a {@link DocSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocSetDTO} or a {@link Page} of {@link DocSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocSetQueryService extends QueryService<DocSet> {

    private final Logger log = LoggerFactory.getLogger(DocSetQueryService.class);

    private final DocSetRepository docSetRepository;

    private final DocSetMapper docSetMapper;

    public DocSetQueryService(DocSetRepository docSetRepository, DocSetMapper docSetMapper) {
        this.docSetRepository = docSetRepository;
        this.docSetMapper = docSetMapper;
    }

    /**
     * Return a {@link List} of {@link DocSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocSetDTO> findByCriteria(DocSetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocSet> specification = createSpecification(criteria);
        return docSetMapper.toDto(docSetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocSetDTO> findByCriteria(DocSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocSet> specification = createSpecification(criteria);
        return docSetRepository.findAll(specification, page).map(docSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocSet> specification = createSpecification(criteria);
        return docSetRepository.count(specification);
    }

    /**
     * Function to convert {@link DocSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocSet> createSpecification(DocSetCriteria criteria) {
        Specification<DocSet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocSet_.id));
            }
            if (criteria.getIdDocSetType() != null) {
                specification = specification.and(buildSpecification(criteria.getIdDocSetType(), DocSet_.idDocSetType));
            }
            if (criteria.getSubTypes() != null) {
                specification = specification.and(buildSpecification(criteria.getSubTypes(), DocSet_.subTypes));
            }
            if (criteria.getTypes() != null) {
                specification = specification.and(buildSpecification(criteria.getTypes(), DocSet_.types));
            }
            if (criteria.getStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStepId(), root -> root.join(DocSet_.step, JoinType.LEFT).get(Step_.id))
                    );
            }
        }
        return specification;
    }
}
