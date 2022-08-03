package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantAddresse} and its DTO {@link ApplicantAddresseDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface ApplicantAddresseMapper extends EntityMapper<ApplicantAddresseDTO, ApplicantAddresse> {
    @Mapping(target = "addresseCountry", source = "addresseCountry", qualifiedByName = "id")
    ApplicantAddresseDTO toDto(ApplicantAddresse s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ApplicantAddresseDTO> toDtoIdSet(Set<ApplicantAddresse> applicantAddresse);
}
