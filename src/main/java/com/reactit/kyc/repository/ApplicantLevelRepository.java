package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantLevelRepository extends JpaRepository<ApplicantLevel, Long>, JpaSpecificationExecutor<ApplicantLevel> {}
