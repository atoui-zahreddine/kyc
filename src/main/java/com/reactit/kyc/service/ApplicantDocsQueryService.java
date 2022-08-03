package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.repository.ApplicantDocsRepository;
import com.reactit.kyc.service.criteria.ApplicantDocsCriteria;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import com.reactit.kyc.service.mapper.ApplicantDocsMapper;
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
 * Service for executing complex queries for {@link ApplicantDocs} entities in the database.
 * The main input is a {@link ApplicantDocsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantDocsDTO} or a {@link Page} of {@link ApplicantDocsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantDocsQueryService extends QueryService<ApplicantDocs> {

    private final Logger log = LoggerFactory.getLogger(ApplicantDocsQueryService.class);

    private final ApplicantDocsRepository applicantDocsRepository;

    private final ApplicantDocsMapper applicantDocsMapper;

    public ApplicantDocsQueryService(ApplicantDocsRepository applicantDocsRepository, ApplicantDocsMapper applicantDocsMapper) {
        this.applicantDocsRepository = applicantDocsRepository;
        this.applicantDocsMapper = applicantDocsMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicantDocsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantDocsDTO> findByCriteria(ApplicantDocsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantDocs> specification = createSpecification(criteria);
        return applicantDocsMapper.toDto(applicantDocsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicantDocsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantDocsDTO> findByCriteria(ApplicantDocsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantDocs> specification = createSpecification(criteria);
        return applicantDocsRepository.findAll(specification, page).map(applicantDocsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantDocsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantDocs> specification = createSpecification(criteria);
        return applicantDocsRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantDocsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantDocs> createSpecification(ApplicantDocsCriteria criteria) {
        Specification<ApplicantDocs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantDocs_.id));
            }
            if (criteria.getDocType() != null) {
                specification = specification.and(buildSpecification(criteria.getDocType(), ApplicantDocs_.docType));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ApplicantDocs_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ApplicantDocs_.lastName));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), ApplicantDocs_.number));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), ApplicantDocs_.dateOfBirth));
            }
            if (criteria.getValidUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUntil(), ApplicantDocs_.validUntil));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), ApplicantDocs_.imageUrl));
            }
            if (criteria.getSubTypes() != null) {
                specification = specification.and(buildSpecification(criteria.getSubTypes(), ApplicantDocs_.subTypes));
            }
            if (criteria.getImageTrust() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageTrust(), ApplicantDocs_.imageTrust));
            }
            if (criteria.getDocsCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocsCountryId(),
                            root -> root.join(ApplicantDocs_.docsCountry, JoinType.LEFT).get(Country_.id)
                        )
                    );
            }
            if (criteria.getApplicantInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantInfoId(),
                            root -> root.join(ApplicantDocs_.applicantInfos, JoinType.LEFT).get(ApplicantInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
