package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantDocsMapperTest {

    private ApplicantDocsMapper applicantDocsMapper;

    @BeforeEach
    public void setUp() {
        applicantDocsMapper = new ApplicantDocsMapperImpl();
    }
}
