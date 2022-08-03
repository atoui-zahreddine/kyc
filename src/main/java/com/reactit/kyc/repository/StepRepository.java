package com.reactit.kyc.repository;

import com.reactit.kyc.domain.Step;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Step entity.
 */
@Repository
public interface StepRepository extends JpaRepository<Step, Long>, JpaSpecificationExecutor<Step> {
    @Query(
        value = "select distinct step from Step step left join fetch step.docSets",
        countQuery = "select count(distinct step) from Step step"
    )
    Page<Step> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct step from Step step left join fetch step.docSets")
    List<Step> findAllWithEagerRelationships();

    @Query("select step from Step step left join fetch step.docSets where step.id =:id")
    Optional<Step> findOneWithEagerRelationships(@Param("id") Long id);
}
