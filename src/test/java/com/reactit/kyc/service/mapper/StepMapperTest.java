package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StepMapperTest {

    private StepMapper stepMapper;

    @BeforeEach
    public void setUp() {
        stepMapper = new StepMapperImpl();
    }
}
