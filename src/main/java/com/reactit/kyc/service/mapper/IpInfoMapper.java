package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.IpInfo;
import com.reactit.kyc.service.dto.IpInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IpInfo} and its DTO {@link IpInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantMapper.class })
public interface IpInfoMapper extends EntityMapper<IpInfoDTO, IpInfo> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "id")
    IpInfoDTO toDto(IpInfo s);
}
