package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantPhone} and its DTO {@link ApplicantPhoneDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantInfoMapper.class })
public interface ApplicantPhoneMapper extends EntityMapper<ApplicantPhoneDTO, ApplicantPhone> {
    @Mapping(target = "applicantInfo", source = "applicantInfo", qualifiedByName = "id")
    ApplicantPhoneDTO toDto(ApplicantPhone s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantPhoneDTO toDtoId(ApplicantPhone applicantPhone);
}
