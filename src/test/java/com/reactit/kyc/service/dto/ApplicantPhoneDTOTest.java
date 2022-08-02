package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantPhoneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantPhoneDTO.class);
        ApplicantPhoneDTO applicantPhoneDTO1 = new ApplicantPhoneDTO();
        applicantPhoneDTO1.setId(1L);
        ApplicantPhoneDTO applicantPhoneDTO2 = new ApplicantPhoneDTO();
        assertThat(applicantPhoneDTO1).isNotEqualTo(applicantPhoneDTO2);
        applicantPhoneDTO2.setId(applicantPhoneDTO1.getId());
        assertThat(applicantPhoneDTO1).isEqualTo(applicantPhoneDTO2);
        applicantPhoneDTO2.setId(2L);
        assertThat(applicantPhoneDTO1).isNotEqualTo(applicantPhoneDTO2);
        applicantPhoneDTO1.setId(null);
        assertThat(applicantPhoneDTO1).isNotEqualTo(applicantPhoneDTO2);
    }
}
