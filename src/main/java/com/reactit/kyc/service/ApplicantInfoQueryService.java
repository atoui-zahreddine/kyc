package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.repository.ApplicantInfoRepository;
import com.reactit.kyc.service.criteria.ApplicantInfoCriteria;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import com.reactit.kyc.service.mapper.ApplicantInfoMapper;
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
 * Service for executing complex queries for {@link ApplicantInfo} entities in the database.
 * The main input is a {@link ApplicantInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantInfoDTO} or a {@link Page} of {@link ApplicantInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantInfoQueryService extends QueryService<ApplicantInfo> {

    private final Logger log = LoggerFactory.getLogger(ApplicantInfoQueryService.class);

    private final ApplicantInfoRepository applicantInfoRepository;

    private final ApplicantInfoMapper applicantInfoMapper;

    public ApplicantInfoQueryService(ApplicantInfoRepository applicantInfoRepository, ApplicantInfoMapper applicantInfoMapper) {
        this.applicantInfoRepository = applicantInfoRepository;
        this.applicantInfoMapper = applicantInfoMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicantInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantInfoDTO> findByCriteria(ApplicantInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantInfo> specification = createSpecification(criteria);
        return applicantInfoMapper.toDto(applicantInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicantInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantInfoDTO> findByCriteria(ApplicantInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantInfo> specification = createSpecification(criteria);
        return applicantInfoRepository.findAll(specification, page).map(applicantInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantInfo> specification = createSpecification(criteria);
        return applicantInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantInfo> createSpecification(ApplicantInfoCriteria criteria) {
        Specification<ApplicantInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantInfo_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ApplicantInfo_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ApplicantInfo_.lastName));
            }
            if (criteria.getAddresses() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddresses(), ApplicantInfo_.addresses));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ApplicantInfo_.email));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), ApplicantInfo_.middleName));
            }
            if (criteria.getStateOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateOfBirth(), ApplicantInfo_.stateOfBirth));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), ApplicantInfo_.dateOfBirth));
            }
            if (criteria.getPlaceOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaceOfBirth(), ApplicantInfo_.placeOfBirth));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), ApplicantInfo_.nationality));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), ApplicantInfo_.gender));
            }
            if (criteria.getApplicantId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantId(),
                            root -> root.join(ApplicantInfo_.applicant, JoinType.LEFT).get(Applicant_.id)
                        )
                    );
            }
            if (criteria.getCountryOfBirthId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCountryOfBirthId(),
                            root -> root.join(ApplicantInfo_.countryOfBirth, JoinType.LEFT).get(Country_.id)
                        )
                    );
            }
            if (criteria.getApplicantAddresseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantAddresseId(),
                            root -> root.join(ApplicantInfo_.applicantAddresses, JoinType.LEFT).get(ApplicantAddresse_.id)
                        )
                    );
            }
            if (criteria.getApplicantPhoneId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantPhoneId(),
                            root -> root.join(ApplicantInfo_.applicantPhones, JoinType.LEFT).get(ApplicantPhone_.id)
                        )
                    );
            }
            if (criteria.getApplicantDocsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantDocsId(),
                            root -> root.join(ApplicantInfo_.applicantDocs, JoinType.LEFT).get(ApplicantDocs_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
