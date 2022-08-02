package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantAddresse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantAddresse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantAddresseRepository extends JpaRepository<ApplicantAddresse, Long>, JpaSpecificationExecutor<ApplicantAddresse> {}
