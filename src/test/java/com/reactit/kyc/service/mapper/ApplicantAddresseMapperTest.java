package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantAddresseMapperTest {

    private ApplicantAddresseMapper applicantAddresseMapper;

    @BeforeEach
    public void setUp() {
        applicantAddresseMapper = new ApplicantAddresseMapperImpl();
    }
}
