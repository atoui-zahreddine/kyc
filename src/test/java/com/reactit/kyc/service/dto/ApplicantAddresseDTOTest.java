package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantAddresseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantAddresseDTO.class);
        ApplicantAddresseDTO applicantAddresseDTO1 = new ApplicantAddresseDTO();
        applicantAddresseDTO1.setId(1L);
        ApplicantAddresseDTO applicantAddresseDTO2 = new ApplicantAddresseDTO();
        assertThat(applicantAddresseDTO1).isNotEqualTo(applicantAddresseDTO2);
        applicantAddresseDTO2.setId(applicantAddresseDTO1.getId());
        assertThat(applicantAddresseDTO1).isEqualTo(applicantAddresseDTO2);
        applicantAddresseDTO2.setId(2L);
        assertThat(applicantAddresseDTO1).isNotEqualTo(applicantAddresseDTO2);
        applicantAddresseDTO1.setId(null);
        assertThat(applicantAddresseDTO1).isNotEqualTo(applicantAddresseDTO2);
    }
}
