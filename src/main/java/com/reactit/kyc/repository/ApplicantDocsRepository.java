package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantDocs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantDocs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantDocsRepository extends JpaRepository<ApplicantDocs, Long>, JpaSpecificationExecutor<ApplicantDocs> {}
