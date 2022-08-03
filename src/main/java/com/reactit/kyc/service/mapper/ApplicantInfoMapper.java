package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantInfo} and its DTO {@link ApplicantInfoDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        ApplicantMapper.class, CountryMapper.class, ApplicantAddresseMapper.class, ApplicantPhoneMapper.class, ApplicantDocsMapper.class,
    }
)
public interface ApplicantInfoMapper extends EntityMapper<ApplicantInfoDTO, ApplicantInfo> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "id")
    @Mapping(target = "countryOfBirth", source = "countryOfBirth", qualifiedByName = "id")
    @Mapping(target = "applicantAddresses", source = "applicantAddresses", qualifiedByName = "idSet")
    @Mapping(target = "applicantPhones", source = "applicantPhones", qualifiedByName = "idSet")
    @Mapping(target = "applicantDocs", source = "applicantDocs", qualifiedByName = "idSet")
    ApplicantInfoDTO toDto(ApplicantInfo s);

    @Mapping(target = "removeApplicantAddresse", ignore = true)
    @Mapping(target = "removeApplicantPhone", ignore = true)
    @Mapping(target = "removeApplicantDocs", ignore = true)
    ApplicantInfo toEntity(ApplicantInfoDTO applicantInfoDTO);
}
