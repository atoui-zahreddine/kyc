package com.reactit.kyc.service;

import com.reactit.kyc.domain.*; // for static metamodels
import com.reactit.kyc.domain.UserAgentInfo;
import com.reactit.kyc.repository.UserAgentInfoRepository;
import com.reactit.kyc.service.criteria.UserAgentInfoCriteria;
import com.reactit.kyc.service.dto.UserAgentInfoDTO;
import com.reactit.kyc.service.mapper.UserAgentInfoMapper;
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
 * Service for executing complex queries for {@link UserAgentInfo} entities in the database.
 * The main input is a {@link UserAgentInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAgentInfoDTO} or a {@link Page} of {@link UserAgentInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAgentInfoQueryService extends QueryService<UserAgentInfo> {

    private final Logger log = LoggerFactory.getLogger(UserAgentInfoQueryService.class);

    private final UserAgentInfoRepository userAgentInfoRepository;

    private final UserAgentInfoMapper userAgentInfoMapper;

    public UserAgentInfoQueryService(UserAgentInfoRepository userAgentInfoRepository, UserAgentInfoMapper userAgentInfoMapper) {
        this.userAgentInfoRepository = userAgentInfoRepository;
        this.userAgentInfoMapper = userAgentInfoMapper;
    }

    /**
     * Return a {@link List} of {@link UserAgentInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAgentInfoDTO> findByCriteria(UserAgentInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAgentInfo> specification = createSpecification(criteria);
        return userAgentInfoMapper.toDto(userAgentInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserAgentInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAgentInfoDTO> findByCriteria(UserAgentInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAgentInfo> specification = createSpecification(criteria);
        return userAgentInfoRepository.findAll(specification, page).map(userAgentInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAgentInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAgentInfo> specification = createSpecification(criteria);
        return userAgentInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAgentInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAgentInfo> createSpecification(UserAgentInfoCriteria criteria) {
        Specification<UserAgentInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAgentInfo_.id));
            }
            if (criteria.getUaBrowser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUaBrowser(), UserAgentInfo_.uaBrowser));
            }
            if (criteria.getUaBrowserVersion() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getUaBrowserVersion(), UserAgentInfo_.uaBrowserVersion));
            }
            if (criteria.getUaDeviceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUaDeviceType(), UserAgentInfo_.uaDeviceType));
            }
            if (criteria.getUaPlatform() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUaPlatform(), UserAgentInfo_.uaPlatform));
            }
            if (criteria.getApplicantId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApplicantId(),
                            root -> root.join(UserAgentInfo_.applicant, JoinType.LEFT).get(Applicant_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
