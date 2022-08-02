package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantDocsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantDocs.class);
        ApplicantDocs applicantDocs1 = new ApplicantDocs();
        applicantDocs1.setId(1L);
        ApplicantDocs applicantDocs2 = new ApplicantDocs();
        applicantDocs2.setId(applicantDocs1.getId());
        assertThat(applicantDocs1).isEqualTo(applicantDocs2);
        applicantDocs2.setId(2L);
        assertThat(applicantDocs1).isNotEqualTo(applicantDocs2);
        applicantDocs1.setId(null);
        assertThat(applicantDocs1).isNotEqualTo(applicantDocs2);
    }
}
