package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.repository.ApplicantLevelRepository;
import com.reactit.kyc.service.criteria.ApplicantLevelCriteria;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import com.reactit.kyc.service.mapper.ApplicantLevelMapper;
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
 * Service for executing complex queries for {@link ApplicantLevel} entities in the database.
 * The main input is a {@link ApplicantLevelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantLevelDTO} or a {@link Page} of {@link ApplicantLevelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantLevelQueryService extends QueryService<ApplicantLevel> {

    private final Logger log = LoggerFactory.getLogger(ApplicantLevelQueryService.class);

    private final ApplicantLevelRepository applicantLevelRepository;

    private final ApplicantLevelMapper applicantLevelMapper;

    public ApplicantLevelQueryService(ApplicantLevelRepository applicantLevelRepository, ApplicantLevelMapper applicantLevelMapper) {
        this.applicantLevelRepository = applicantLevelRepository;
        this.applicantLevelMapper = applicantLevelMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicantLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantLevelDTO> findByCriteria(ApplicantLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantLevel> specification = createSpecification(criteria);
        return applicantLevelMapper.toDto(applicantLevelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicantLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantLevelDTO> findByCriteria(ApplicantLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantLevel> specification = createSpecification(criteria);
        return applicantLevelRepository.findAll(specification, page).map(applicantLevelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantLevelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantLevel> specification = createSpecification(criteria);
        return applicantLevelRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantLevelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantLevel> createSpecification(ApplicantLevelCriteria criteria) {
        Specification<ApplicantLevel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantLevel_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ApplicantLevel_.code));
            }
            if (criteria.getLevelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLevelName(), ApplicantLevel_.levelName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ApplicantLevel_.description));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), ApplicantLevel_.url));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ApplicantLevel_.createdAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), ApplicantLevel_.createdBy));
            }
            if (criteria.getModifiedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedAt(), ApplicantLevel_.modifiedAt));
            }
            if (criteria.getStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStepId(), root -> root.join(ApplicantLevel_.steps, JoinType.LEFT).get(Step_.id))
                    );
            }
            if (criteria.getApplicantId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantId(),
                            root -> root.join(ApplicantLevel_.applicants, JoinType.LEFT).get(Applicant_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
