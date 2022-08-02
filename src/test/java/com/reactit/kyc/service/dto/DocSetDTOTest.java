package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocSetDTO.class);
        DocSetDTO docSetDTO1 = new DocSetDTO();
        docSetDTO1.setId(1L);
        DocSetDTO docSetDTO2 = new DocSetDTO();
        assertThat(docSetDTO1).isNotEqualTo(docSetDTO2);
        docSetDTO2.setId(docSetDTO1.getId());
        assertThat(docSetDTO1).isEqualTo(docSetDTO2);
        docSetDTO2.setId(2L);
        assertThat(docSetDTO1).isNotEqualTo(docSetDTO2);
        docSetDTO1.setId(null);
        assertThat(docSetDTO1).isNotEqualTo(docSetDTO2);
    }
}
