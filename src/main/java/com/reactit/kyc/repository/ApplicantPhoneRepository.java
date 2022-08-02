package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantPhone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantPhoneRepository extends JpaRepository<ApplicantPhone, Long>, JpaSpecificationExecutor<ApplicantPhone> {}
