package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantLevel} and its DTO {@link ApplicantLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantMapper.class })
public interface ApplicantLevelMapper extends EntityMapper<ApplicantLevelDTO, ApplicantLevel> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "id")
    ApplicantLevelDTO toDto(ApplicantLevel s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ApplicantLevelDTO> toDtoIdSet(Set<ApplicantLevel> applicantLevel);
}
