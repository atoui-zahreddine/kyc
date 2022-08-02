package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantAddresse} and its DTO {@link ApplicantAddresseDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantInfoMapper.class })
public interface ApplicantAddresseMapper extends EntityMapper<ApplicantAddresseDTO, ApplicantAddresse> {
    @Mapping(target = "applicantInfo", source = "applicantInfo", qualifiedByName = "id")
    ApplicantAddresseDTO toDto(ApplicantAddresse s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantAddresseDTO toDtoId(ApplicantAddresse applicantAddresse);
}
