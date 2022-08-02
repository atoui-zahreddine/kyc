package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantDocsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantDocsDTO.class);
        ApplicantDocsDTO applicantDocsDTO1 = new ApplicantDocsDTO();
        applicantDocsDTO1.setId(1L);
        ApplicantDocsDTO applicantDocsDTO2 = new ApplicantDocsDTO();
        assertThat(applicantDocsDTO1).isNotEqualTo(applicantDocsDTO2);
        applicantDocsDTO2.setId(applicantDocsDTO1.getId());
        assertThat(applicantDocsDTO1).isEqualTo(applicantDocsDTO2);
        applicantDocsDTO2.setId(2L);
        assertThat(applicantDocsDTO1).isNotEqualTo(applicantDocsDTO2);
        applicantDocsDTO1.setId(null);
        assertThat(applicantDocsDTO1).isNotEqualTo(applicantDocsDTO2);
    }
}
