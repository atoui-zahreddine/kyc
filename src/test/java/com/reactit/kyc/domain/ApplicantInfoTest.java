package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantInfo.class);
        ApplicantInfo applicantInfo1 = new ApplicantInfo();
        applicantInfo1.setId(1L);
        ApplicantInfo applicantInfo2 = new ApplicantInfo();
        applicantInfo2.setId(applicantInfo1.getId());
        assertThat(applicantInfo1).isEqualTo(applicantInfo2);
        applicantInfo2.setId(2L);
        assertThat(applicantInfo1).isNotEqualTo(applicantInfo2);
        applicantInfo1.setId(null);
        assertThat(applicantInfo1).isNotEqualTo(applicantInfo2);
    }
}
