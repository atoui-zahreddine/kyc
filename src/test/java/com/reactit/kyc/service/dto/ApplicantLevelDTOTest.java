package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantLevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantLevelDTO.class);
        ApplicantLevelDTO applicantLevelDTO1 = new ApplicantLevelDTO();
        applicantLevelDTO1.setId(1L);
        ApplicantLevelDTO applicantLevelDTO2 = new ApplicantLevelDTO();
        assertThat(applicantLevelDTO1).isNotEqualTo(applicantLevelDTO2);
        applicantLevelDTO2.setId(applicantLevelDTO1.getId());
        assertThat(applicantLevelDTO1).isEqualTo(applicantLevelDTO2);
        applicantLevelDTO2.setId(2L);
        assertThat(applicantLevelDTO1).isNotEqualTo(applicantLevelDTO2);
        applicantLevelDTO1.setId(null);
        assertThat(applicantLevelDTO1).isNotEqualTo(applicantLevelDTO2);
    }
}
