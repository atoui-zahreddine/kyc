package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.repository.ApplicantAddresseRepository;
import com.reactit.kyc.service.criteria.ApplicantAddresseCriteria;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import com.reactit.kyc.service.mapper.ApplicantAddresseMapper;
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
 * Service for executing complex queries for {@link ApplicantAddresse} entities in the database.
 * The main input is a {@link ApplicantAddresseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantAddresseDTO} or a {@link Page} of {@link ApplicantAddresseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantAddresseQueryService extends QueryService<ApplicantAddresse> {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddresseQueryService.class);

    private final ApplicantAddresseRepository applicantAddresseRepository;

    private final ApplicantAddresseMapper applicantAddresseMapper;

    public ApplicantAddresseQueryService(
        ApplicantAddresseRepository applicantAddresseRepository,
        ApplicantAddresseMapper applicantAddresseMapper
    ) {
        this.applicantAddresseRepository = applicantAddresseRepository;
        this.applicantAddresseMapper = applicantAddresseMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicantAddresseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantAddresseDTO> findByCriteria(ApplicantAddresseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantAddresse> specification = createSpecification(criteria);
        return applicantAddresseMapper.toDto(applicantAddresseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicantAddresseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantAddresseDTO> findByCriteria(ApplicantAddresseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantAddresse> specification = createSpecification(criteria);
        return applicantAddresseRepository.findAll(specification, page).map(applicantAddresseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantAddresseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantAddresse> specification = createSpecification(criteria);
        return applicantAddresseRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantAddresseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantAddresse> createSpecification(ApplicantAddresseCriteria criteria) {
        Specification<ApplicantAddresse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantAddresse_.id));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostCode(), ApplicantAddresse_.postCode));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), ApplicantAddresse_.state));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), ApplicantAddresse_.street));
            }
            if (criteria.getSubStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubStreet(), ApplicantAddresse_.subStreet));
            }
            if (criteria.getTown() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTown(), ApplicantAddresse_.town));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), ApplicantAddresse_.enabled));
            }
            if (criteria.getApplicantInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantInfoId(),
                            root -> root.join(ApplicantAddresse_.applicantInfo, JoinType.LEFT).get(ApplicantInfo_.id)
                        )
                    );
            }
            if (criteria.getAddresseCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddresseCountryId(),
                            root -> root.join(ApplicantAddresse_.addresseCountries, JoinType.LEFT).get(Country_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
