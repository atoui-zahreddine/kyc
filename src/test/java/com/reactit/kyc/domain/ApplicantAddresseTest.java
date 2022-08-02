package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantAddresseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantAddresse.class);
        ApplicantAddresse applicantAddresse1 = new ApplicantAddresse();
        applicantAddresse1.setId(1L);
        ApplicantAddresse applicantAddresse2 = new ApplicantAddresse();
        applicantAddresse2.setId(applicantAddresse1.getId());
        assertThat(applicantAddresse1).isEqualTo(applicantAddresse2);
        applicantAddresse2.setId(2L);
        assertThat(applicantAddresse1).isNotEqualTo(applicantAddresse2);
        applicantAddresse1.setId(null);
        assertThat(applicantAddresse1).isNotEqualTo(applicantAddresse2);
    }
}
