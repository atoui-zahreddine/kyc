package com.reactit.kyc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocSetMapperTest {

    private DocSetMapper docSetMapper;

    @BeforeEach
    public void setUp() {
        docSetMapper = new DocSetMapperImpl();
    }
}
