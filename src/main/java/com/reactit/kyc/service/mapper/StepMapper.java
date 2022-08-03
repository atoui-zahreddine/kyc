package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.Step;
import com.reactit.kyc.service.dto.StepDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Step} and its DTO {@link StepDTO}.
 */
@Mapper(componentModel = "spring", uses = { DocSetMapper.class })
public interface StepMapper extends EntityMapper<StepDTO, Step> {
    @Mapping(target = "docSets", source = "docSets", qualifiedByName = "idSet")
    StepDTO toDto(Step s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<StepDTO> toDtoIdSet(Set<Step> step);

    @Mapping(target = "removeDocSet", ignore = true)
    Step toEntity(StepDTO stepDTO);
}
