package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.Country;
import com.reactit.kyc.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { ApplicantAddresseMapper.class, ApplicantDocsMapper.class, ApplicantInfoMapper.class, ApplicantPhoneMapper.class }
)
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "addresses", source = "addresses", qualifiedByName = "id")
    @Mapping(target = "docs", source = "docs", qualifiedByName = "id")
    @Mapping(target = "applicants", source = "applicants", qualifiedByName = "id")
    @Mapping(target = "phones", source = "phones", qualifiedByName = "id")
    CountryDTO toDto(Country s);
}
