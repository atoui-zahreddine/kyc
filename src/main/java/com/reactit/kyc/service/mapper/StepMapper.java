package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.Step;
import com.reactit.kyc.service.dto.StepDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Step} and its DTO {@link StepDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantLevelMapper.class })
public interface StepMapper extends EntityMapper<StepDTO, Step> {
    @Mapping(target = "applicantLevels", source = "applicantLevels", qualifiedByName = "idSet")
    StepDTO toDto(Step s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StepDTO toDtoId(Step step);

    @Mapping(target = "removeApplicantLevel", ignore = true)
    Step toEntity(StepDTO stepDTO);
}
