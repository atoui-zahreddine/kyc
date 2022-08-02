package com.reactit.kyc.service.mapper;

import com.reactit.kyc.domain.UserAgentInfo;
import com.reactit.kyc.service.dto.UserAgentInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAgentInfo} and its DTO {@link UserAgentInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicantMapper.class })
public interface UserAgentInfoMapper extends EntityMapper<UserAgentInfoDTO, UserAgentInfo> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "id")
    UserAgentInfoDTO toDto(UserAgentInfo s);
}
