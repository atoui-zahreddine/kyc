package com.reactit.kyc.repository;

import com.reactit.kyc.domain.ApplicantInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicantInfo entity.
 */
@Repository
public interface ApplicantInfoRepository extends JpaRepository<ApplicantInfo, Long>, JpaSpecificationExecutor<ApplicantInfo> {
    @Query(
        value = "select distinct applicantInfo from ApplicantInfo applicantInfo left join fetch applicantInfo.applicantAddresses left join fetch applicantInfo.applicantPhones left join fetch applicantInfo.applicantDocs",
        countQuery = "select count(distinct applicantInfo) from ApplicantInfo applicantInfo"
    )
    Page<ApplicantInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct applicantInfo from ApplicantInfo applicantInfo left join fetch applicantInfo.applicantAddresses left join fetch applicantInfo.applicantPhones left join fetch applicantInfo.applicantDocs"
    )
    List<ApplicantInfo> findAllWithEagerRelationships();

    @Query(
        "select applicantInfo from ApplicantInfo applicantInfo left join fetch applicantInfo.applicantAddresses left join fetch applicantInfo.applicantPhones left join fetch applicantInfo.applicantDocs where applicantInfo.id =:id"
    )
    Optional<ApplicantInfo> findOneWithEagerRelationships(@Param("id") Long id);
}
