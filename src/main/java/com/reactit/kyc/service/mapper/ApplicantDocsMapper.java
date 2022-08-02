package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantDocs} and its DTO {@link ApplicantDocsDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantInfoMapper.class })
public interface ApplicantDocsMapper extends EntityMapper<ApplicantDocsDTO, ApplicantDocs> {
    @Mapping(target = "applicantInfo", source = "applicantInfo", qualifiedByName = "id")
    ApplicantDocsDTO toDto(ApplicantDocs s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantDocsDTO toDtoId(ApplicantDocs applicantDocs);
}
