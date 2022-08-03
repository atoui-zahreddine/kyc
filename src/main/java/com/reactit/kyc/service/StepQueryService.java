package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.Step;
import com.reactit.kyc.repository.StepRepository;
import com.reactit.kyc.service.criteria.StepCriteria;
import com.reactit.kyc.service.dto.StepDTO;
import com.reactit.kyc.service.mapper.StepMapper;
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
 * Service for executing complex queries for {@link Step} entities in the database.
 * The main input is a {@link StepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StepDTO} or a {@link Page} of {@link StepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StepQueryService extends QueryService<Step> {

    private final Logger log = LoggerFactory.getLogger(StepQueryService.class);

    private final StepRepository stepRepository;

    private final StepMapper stepMapper;

    public StepQueryService(StepRepository stepRepository, StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
    }

    /**
     * Return a {@link List} of {@link StepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StepDTO> findByCriteria(StepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Step> specification = createSpecification(criteria);
        return stepMapper.toDto(stepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StepDTO> findByCriteria(StepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Step> specification = createSpecification(criteria);
        return stepRepository.findAll(specification, page).map(stepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Step> specification = createSpecification(criteria);
        return stepRepository.count(specification);
    }

    /**
     * Function to convert {@link StepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Step> createSpecification(StepCriteria criteria) {
        Specification<Step> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Step_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Step_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Step_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Step_.description));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Step_.createdAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), Step_.createdBy));
            }
            if (criteria.getModifiedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedAt(), Step_.modifiedAt));
            }
            if (criteria.getDocSetId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocSetId(), root -> root.join(Step_.docSets, JoinType.LEFT).get(DocSet_.id))
                    );
            }
            if (criteria.getApplicantLevelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantLevelId(),
                            root -> root.join(Step_.applicantLevels, JoinType.LEFT).get(ApplicantLevel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
