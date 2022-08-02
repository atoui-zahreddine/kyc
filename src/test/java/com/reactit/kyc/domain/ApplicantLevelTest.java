package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantLevel.class);
        ApplicantLevel applicantLevel1 = new ApplicantLevel();
        applicantLevel1.setId(1L);
        ApplicantLevel applicantLevel2 = new ApplicantLevel();
        applicantLevel2.setId(applicantLevel1.getId());
        assertThat(applicantLevel1).isEqualTo(applicantLevel2);
        applicantLevel2.setId(2L);
        assertThat(applicantLevel1).isNotEqualTo(applicantLevel2);
        applicantLevel1.setId(null);
        assertThat(applicantLevel1).isNotEqualTo(applicantLevel2);
    }
}
