package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocSet.class);
        DocSet docSet1 = new DocSet();
        docSet1.setId(1L);
        DocSet docSet2 = new DocSet();
        docSet2.setId(docSet1.getId());
        assertThat(docSet1).isEqualTo(docSet2);
        docSet2.setId(2L);
        assertThat(docSet1).isNotEqualTo(docSet2);
        docSet1.setId(null);
        assertThat(docSet1).isNotEqualTo(docSet2);
    }
}
