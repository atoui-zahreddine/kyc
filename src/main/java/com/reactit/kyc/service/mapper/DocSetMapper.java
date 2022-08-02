package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.service.dto.DocSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocSet} and its DTO {@link DocSetDTO}.
 */
@Mapper(componentModel = "spring", uses = { StepMapper.class })
public interface DocSetMapper extends EntityMapper<DocSetDTO, DocSet> {
    @Mapping(target = "step", source = "step", qualifiedByName = "id")
    DocSetDTO toDto(DocSet s);
}
