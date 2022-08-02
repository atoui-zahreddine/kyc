package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IpInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IpInfoDTO.class);
        IpInfoDTO ipInfoDTO1 = new IpInfoDTO();
        ipInfoDTO1.setId(1L);
        IpInfoDTO ipInfoDTO2 = new IpInfoDTO();
        assertThat(ipInfoDTO1).isNotEqualTo(ipInfoDTO2);
        ipInfoDTO2.setId(ipInfoDTO1.getId());
        assertThat(ipInfoDTO1).isEqualTo(ipInfoDTO2);
        ipInfoDTO2.setId(2L);
        assertThat(ipInfoDTO1).isNotEqualTo(ipInfoDTO2);
        ipInfoDTO1.setId(null);
        assertThat(ipInfoDTO1).isNotEqualTo(ipInfoDTO2);
    }
}
