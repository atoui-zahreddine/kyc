package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantInfoDTO.class);
        ApplicantInfoDTO applicantInfoDTO1 = new ApplicantInfoDTO();
        applicantInfoDTO1.setId(1L);
        ApplicantInfoDTO applicantInfoDTO2 = new ApplicantInfoDTO();
        assertThat(applicantInfoDTO1).isNotEqualTo(applicantInfoDTO2);
        applicantInfoDTO2.setId(applicantInfoDTO1.getId());
        assertThat(applicantInfoDTO1).isEqualTo(applicantInfoDTO2);
        applicantInfoDTO2.setId(2L);
        assertThat(applicantInfoDTO1).isNotEqualTo(applicantInfoDTO2);
        applicantInfoDTO1.setId(null);
        assertThat(applicantInfoDTO1).isNotEqualTo(applicantInfoDTO2);
    }
}
