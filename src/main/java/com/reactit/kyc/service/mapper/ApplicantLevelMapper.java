package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantLevel} and its DTO {@link ApplicantLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = { StepMapper.class })
public interface ApplicantLevelMapper extends EntityMapper<ApplicantLevelDTO, ApplicantLevel> {
    @Mapping(target = "steps", source = "steps", qualifiedByName = "idSet")
    ApplicantLevelDTO toDto(ApplicantLevel s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantLevelDTO toDtoId(ApplicantLevel applicantLevel);

    @Mapping(target = "removeStep", ignore = true)
    ApplicantLevel toEntity(ApplicantLevelDTO applicantLevelDTO);
}
