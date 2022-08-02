package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IpInfoMapperTest {

    private IpInfoMapper ipInfoMapper;

    @BeforeEach
    public void setUp() {
        ipInfoMapper = new IpInfoMapperImpl();
    }
}
