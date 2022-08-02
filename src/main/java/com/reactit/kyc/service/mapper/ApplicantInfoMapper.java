package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantInfo} and its DTO {@link ApplicantInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantMapper.class })
public interface ApplicantInfoMapper extends EntityMapper<ApplicantInfoDTO, ApplicantInfo> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "id")
    ApplicantInfoDTO toDto(ApplicantInfo s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantInfoDTO toDtoId(ApplicantInfo applicantInfo);
}
