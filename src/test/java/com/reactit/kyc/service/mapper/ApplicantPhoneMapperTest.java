package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantPhoneMapperTest {

    private ApplicantPhoneMapper applicantPhoneMapper;

    @BeforeEach
    public void setUp() {
        applicantPhoneMapper = new ApplicantPhoneMapperImpl();
    }
}
