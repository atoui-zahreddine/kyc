package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.service.dto.DocSetDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocSet} and its DTO {@link DocSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocSetMapper extends EntityMapper<DocSetDTO, DocSet> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<DocSetDTO> toDtoIdSet(Set<DocSet> docSet);
}
