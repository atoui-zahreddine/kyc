package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantPhone} and its DTO {@link ApplicantPhoneDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface ApplicantPhoneMapper extends EntityMapper<ApplicantPhoneDTO, ApplicantPhone> {
    @Mapping(target = "phoneCountry", source = "phoneCountry", qualifiedByName = "id")
    ApplicantPhoneDTO toDto(ApplicantPhone s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ApplicantPhoneDTO> toDtoIdSet(Set<ApplicantPhone> applicantPhone);
}
