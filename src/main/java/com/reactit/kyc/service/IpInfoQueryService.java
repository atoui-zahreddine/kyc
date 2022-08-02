package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.IpInfo;
import com.reactit.kyc.repository.IpInfoRepository;
import com.reactit.kyc.service.criteria.IpInfoCriteria;
import com.reactit.kyc.service.dto.IpInfoDTO;
import com.reactit.kyc.service.mapper.IpInfoMapper;
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
 * Service for executing complex queries for {@link IpInfo} entities in the database.
 * The main input is a {@link IpInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IpInfoDTO} or a {@link Page} of {@link IpInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IpInfoQueryService extends QueryService<IpInfo> {

    private final Logger log = LoggerFactory.getLogger(IpInfoQueryService.class);

    private final IpInfoRepository ipInfoRepository;

    private final IpInfoMapper ipInfoMapper;

    public IpInfoQueryService(IpInfoRepository ipInfoRepository, IpInfoMapper ipInfoMapper) {
        this.ipInfoRepository = ipInfoRepository;
        this.ipInfoMapper = ipInfoMapper;
    }

    /**
     * Return a {@link List} of {@link IpInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IpInfoDTO> findByCriteria(IpInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IpInfo> specification = createSpecification(criteria);
        return ipInfoMapper.toDto(ipInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IpInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IpInfoDTO> findByCriteria(IpInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IpInfo> specification = createSpecification(criteria);
        return ipInfoRepository.findAll(specification, page).map(ipInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IpInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IpInfo> specification = createSpecification(criteria);
        return ipInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link IpInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IpInfo> createSpecification(IpInfoCriteria criteria) {
        Specification<IpInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IpInfo_.id));
            }
            if (criteria.getAsn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAsn(), IpInfo_.asn));
            }
            if (criteria.getAsnOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAsnOrg(), IpInfo_.asnOrg));
            }
            if (criteria.getCountryCode2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode2(), IpInfo_.countryCode2));
            }
            if (criteria.getCountryCode3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode3(), IpInfo_.countryCode3));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), IpInfo_.ip));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), IpInfo_.lat));
            }
            if (criteria.getLon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLon(), IpInfo_.lon));
            }
            if (criteria.getApplicantId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantId(),
                            root -> root.join(IpInfo_.applicant, JoinType.LEFT).get(Applicant_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
