package com.reactit.kyc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IpInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IpInfo.class);
        IpInfo ipInfo1 = new IpInfo();
        ipInfo1.setId(1L);
        IpInfo ipInfo2 = new IpInfo();
        ipInfo2.setId(ipInfo1.getId());
        assertThat(ipInfo1).isEqualTo(ipInfo2);
        ipInfo2.setId(2L);
        assertThat(ipInfo1).isNotEqualTo(ipInfo2);
        ipInfo1.setId(null);
        assertThat(ipInfo1).isNotEqualTo(ipInfo2);
    }
}
