package com.reactit.kyc.repository;

import com.reactit.kyc.domain.DocSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocSetRepository extends JpaRepository<DocSet, Long>, JpaSpecificationExecutor<DocSet> {}
