package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAgentInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAgentInfo.class);
        UserAgentInfo userAgentInfo1 = new UserAgentInfo();
        userAgentInfo1.setId(1L);
        UserAgentInfo userAgentInfo2 = new UserAgentInfo();
        userAgentInfo2.setId(userAgentInfo1.getId());
        assertThat(userAgentInfo1).isEqualTo(userAgentInfo2);
        userAgentInfo2.setId(2L);
        assertThat(userAgentInfo1).isNotEqualTo(userAgentInfo2);
        userAgentInfo1.setId(null);
        assertThat(userAgentInfo1).isNotEqualTo(userAgentInfo2);
    }
}
