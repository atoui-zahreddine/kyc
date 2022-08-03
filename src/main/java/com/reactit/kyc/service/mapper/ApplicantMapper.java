package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.service.dto.ApplicantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Applicant} and its DTO {@link ApplicantDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantLevelMapper.class })
public interface ApplicantMapper extends EntityMapper<ApplicantDTO, Applicant> {
    @Mapping(target = "applicantLevel", source = "applicantLevel", qualifiedByName = "id")
    ApplicantDTO toDto(Applicant s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantDTO toDtoId(Applicant applicant);
}
