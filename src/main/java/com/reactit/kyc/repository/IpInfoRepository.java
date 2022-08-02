package com.reactit.kyc.repository;

import com.reactit.kyc.domain.IpInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IpInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IpInfoRepository extends JpaRepository<IpInfo, Long>, JpaSpecificationExecutor<IpInfo> {}
