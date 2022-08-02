package com.reactit.kyc.repository;

import com.reactit.kyc.domain.UserAgentInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserAgentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAgentInfoRepository extends JpaRepository<UserAgentInfo, Long>, JpaSpecificationExecutor<UserAgentInfo> {}
