package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantLevel entity.
 */
@Repository
public interface ApplicantLevelRepository extends JpaRepository<ApplicantLevel, Long>, JpaSpecificationExecutor<ApplicantLevel> {
    @Query(
        value = "select distinct applicantLevel from ApplicantLevel applicantLevel left join fetch applicantLevel.steps",
        countQuery = "select count(distinct applicantLevel) from ApplicantLevel applicantLevel"
    )
    Page<ApplicantLevel> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct applicantLevel from ApplicantLevel applicantLevel left join fetch applicantLevel.steps")
    List<ApplicantLevel> findAllWithEagerRelationships();

    @Query("select applicantLevel from ApplicantLevel applicantLevel left join fetch applicantLevel.steps where applicantLevel.id =:id")
    Optional<ApplicantLevel> findOneWithEagerRelationships(@Param("id") Long id);
}
