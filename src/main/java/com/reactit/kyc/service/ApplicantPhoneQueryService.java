package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.repository.ApplicantPhoneRepository;
import com.reactit.kyc.service.criteria.ApplicantPhoneCriteria;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import com.reactit.kyc.service.mapper.ApplicantPhoneMapper;
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
 * Service for executing complex queries for {@link ApplicantPhone} entities in the database.
 * The main input is a {@link ApplicantPhoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantPhoneDTO} or a {@link Page} of {@link ApplicantPhoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantPhoneQueryService extends QueryService<ApplicantPhone> {

    private final Logger log = LoggerFactory.getLogger(ApplicantPhoneQueryService.class);

    private final ApplicantPhoneRepository applicantPhoneRepository;

    private final ApplicantPhoneMapper applicantPhoneMapper;

    public ApplicantPhoneQueryService(ApplicantPhoneRepository applicantPhoneRepository, ApplicantPhoneMapper applicantPhoneMapper) {
        this.applicantPhoneRepository = applicantPhoneRepository;
        this.applicantPhoneMapper = applicantPhoneMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicantPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantPhoneDTO> findByCriteria(ApplicantPhoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantPhone> specification = createSpecification(criteria);
        return applicantPhoneMapper.toDto(applicantPhoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicantPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantPhoneDTO> findByCriteria(ApplicantPhoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantPhone> specification = createSpecification(criteria);
        return applicantPhoneRepository.findAll(specification, page).map(applicantPhoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantPhoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantPhone> specification = createSpecification(criteria);
        return applicantPhoneRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantPhoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantPhone> createSpecification(ApplicantPhoneCriteria criteria) {
        Specification<ApplicantPhone> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantPhone_.id));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), ApplicantPhone_.country));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), ApplicantPhone_.number));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), ApplicantPhone_.enabled));
            }
            if (criteria.getPhoneCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPhoneCountryId(),
                            root -> root.join(ApplicantPhone_.phoneCountry, JoinType.LEFT).get(Country_.id)
                        )
                    );
            }
            if (criteria.getApplicantInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantInfoId(),
                            root -> root.join(ApplicantPhone_.applicantInfos, JoinType.LEFT).get(ApplicantInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
