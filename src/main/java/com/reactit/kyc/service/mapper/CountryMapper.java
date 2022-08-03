package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.Country;
import com.reactit.kyc.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoId(Country country);
}
