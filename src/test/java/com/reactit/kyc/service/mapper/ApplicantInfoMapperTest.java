package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantInfoMapperTest {

    private ApplicantInfoMapper applicantInfoMapper;

    @BeforeEach
    public void setUp() {
        applicantInfoMapper = new ApplicantInfoMapperImpl();
    }
}
