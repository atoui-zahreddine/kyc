package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantDocs} and its DTO {@link ApplicantDocsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface ApplicantDocsMapper extends EntityMapper<ApplicantDocsDTO, ApplicantDocs> {
    @Mapping(target = "docsCountry", source = "docsCountry", qualifiedByName = "id")
    ApplicantDocsDTO toDto(ApplicantDocs s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ApplicantDocsDTO> toDtoIdSet(Set<ApplicantDocs> applicantDocs);
}
