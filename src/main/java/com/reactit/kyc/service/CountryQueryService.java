package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.repository.CountryRepository;
import com.reactit.kyc.service.criteria.CountryCriteria;
import com.reactit.kyc.service.dto.CountryDTO;
import com.reactit.kyc.service.mapper.CountryMapper;
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
 * Service for executing complex queries for {@link Country} entities in the database.
 * The main input is a {@link CountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountryDTO} or a {@link Page} of {@link CountryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryQueryService extends QueryService<Country> {

    private final Logger log = LoggerFactory.getLogger(CountryQueryService.class);

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public CountryQueryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    /**
     * Return a {@link List} of {@link CountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountryDTO> findByCriteria(CountryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryMapper.toDto(countryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryDTO> findByCriteria(CountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.findAll(specification, page).map(countryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.count(specification);
    }

    /**
     * Function to convert {@link CountryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Country> createSpecification(CountryCriteria criteria) {
        Specification<Country> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Country_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Country_.name));
            }
            if (criteria.getCountryCode2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode2(), Country_.countryCode2));
            }
            if (criteria.getCountryCode3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode3(), Country_.countryCode3));
            }
            if (criteria.getPhoneCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneCode(), Country_.phoneCode));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildSpecification(criteria.getRegion(), Country_.region));
            }
            if (criteria.getAddressesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressesId(),
                            root -> root.join(Country_.addresses, JoinType.LEFT).get(ApplicantAddresse_.id)
                        )
                    );
            }
            if (criteria.getDocsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocsId(), root -> root.join(Country_.docs, JoinType.LEFT).get(ApplicantDocs_.id))
                    );
            }
            if (criteria.getApplicantsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantsId(),
                            root -> root.join(Country_.applicants, JoinType.LEFT).get(ApplicantInfo_.id)
                        )
                    );
            }
            if (criteria.getPhonesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPhonesId(),
                            root -> root.join(Country_.phones, JoinType.LEFT).get(ApplicantPhone_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
