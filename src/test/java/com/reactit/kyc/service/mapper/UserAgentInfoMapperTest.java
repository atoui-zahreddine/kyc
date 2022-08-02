package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAgentInfoMapperTest {

    private UserAgentInfoMapper userAgentInfoMapper;

    @BeforeEach
    public void setUp() {
        userAgentInfoMapper = new UserAgentInfoMapperImpl();
    }
}
