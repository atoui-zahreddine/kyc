package com.reactit.kyc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.kyc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAgentInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAgentInfoDTO.class);
        UserAgentInfoDTO userAgentInfoDTO1 = new UserAgentInfoDTO();
        userAgentInfoDTO1.setId(1L);
        UserAgentInfoDTO userAgentInfoDTO2 = new UserAgentInfoDTO();
        assertThat(userAgentInfoDTO1).isNotEqualTo(userAgentInfoDTO2);
        userAgentInfoDTO2.setId(userAgentInfoDTO1.getId());
        assertThat(userAgentInfoDTO1).isEqualTo(userAgentInfoDTO2);
        userAgentInfoDTO2.setId(2L);
        assertThat(userAgentInfoDTO1).isNotEqualTo(userAgentInfoDTO2);
        userAgentInfoDTO1.setId(null);
        assertThat(userAgentInfoDTO1).isNotEqualTo(userAgentInfoDTO2);
    }
}
