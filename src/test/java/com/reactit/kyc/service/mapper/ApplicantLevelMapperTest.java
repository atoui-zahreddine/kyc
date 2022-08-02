package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantLevelMapperTest {

    private ApplicantLevelMapper applicantLevelMapper;

    @BeforeEach
    public void setUp() {
        applicantLevelMapper = new ApplicantLevelMapperImpl();
    }
}
