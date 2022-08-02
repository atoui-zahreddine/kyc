package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantInfoRepository extends JpaRepository<ApplicantInfo, Long>, JpaSpecificationExecutor<ApplicantInfo> {}
