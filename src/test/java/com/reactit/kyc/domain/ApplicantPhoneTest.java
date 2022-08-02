package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantPhoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantPhone.class);
        ApplicantPhone applicantPhone1 = new ApplicantPhone();
        applicantPhone1.setId(1L);
        ApplicantPhone applicantPhone2 = new ApplicantPhone();
        applicantPhone2.setId(applicantPhone1.getId());
        assertThat(applicantPhone1).isEqualTo(applicantPhone2);
        applicantPhone2.setId(2L);
        assertThat(applicantPhone1).isNotEqualTo(applicantPhone2);
        applicantPhone1.setId(null);
        assertThat(applicantPhone1).isNotEqualTo(applicantPhone2);
    }
}
