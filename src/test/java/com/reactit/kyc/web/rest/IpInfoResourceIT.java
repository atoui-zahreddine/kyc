package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.domain.IpInfo;
import com.reactit.kyc.repository.IpInfoRepository;
import com.reactit.kyc.service.criteria.IpInfoCriteria;
import com.reactit.kyc.service.dto.IpInfoDTO;
import com.reactit.kyc.service.mapper.IpInfoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IpInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IpInfoResourceIT {

    private static final Long DEFAULT_ASN = 1L;
    private static final Long UPDATED_ASN = 2L;
    private static final Long SMALLER_ASN = 1L - 1L;

    private static final String DEFAULT_ASN_ORG = "AAAAAAAAAA";
    private static final String UPDATED_ASN_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_2 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_3 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;
    private static final Double SMALLER_LON = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/ip-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Autowired
    private IpInfoMapper ipInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIpInfoMockMvc;

    private IpInfo ipInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IpInfo createEntity(EntityManager em) {
        IpInfo ipInfo = new IpInfo()
            .asn(DEFAULT_ASN)
            .asnOrg(DEFAULT_ASN_ORG)
            .countryCode2(DEFAULT_COUNTRY_CODE_2)
            .countryCode3(DEFAULT_COUNTRY_CODE_3)
            .ip(DEFAULT_IP)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON);
        return ipInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IpInfo createUpdatedEntity(EntityManager em) {
        IpInfo ipInfo = new IpInfo()
            .asn(UPDATED_ASN)
            .asnOrg(UPDATED_ASN_ORG)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .ip(UPDATED_IP)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);
        return ipInfo;
    }

    @BeforeEach
    public void initTest() {
        ipInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createIpInfo() throws Exception {
        int databaseSizeBeforeCreate = ipInfoRepository.findAll().size();
        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);
        restIpInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeCreate + 1);
        IpInfo testIpInfo = ipInfoList.get(ipInfoList.size() - 1);
        assertThat(testIpInfo.getAsn()).isEqualTo(DEFAULT_ASN);
        assertThat(testIpInfo.getAsnOrg()).isEqualTo(DEFAULT_ASN_ORG);
        assertThat(testIpInfo.getCountryCode2()).isEqualTo(DEFAULT_COUNTRY_CODE_2);
        assertThat(testIpInfo.getCountryCode3()).isEqualTo(DEFAULT_COUNTRY_CODE_3);
        assertThat(testIpInfo.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testIpInfo.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testIpInfo.getLon()).isEqualTo(DEFAULT_LON);
    }

    @Test
    @Transactional
    void createIpInfoWithExistingId() throws Exception {
        // Create the IpInfo with an existing ID
        ipInfo.setId(1L);
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        int databaseSizeBeforeCreate = ipInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIpInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIpInfos() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ipInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].asn").value(hasItem(DEFAULT_ASN.intValue())))
            .andExpect(jsonPath("$.[*].asnOrg").value(hasItem(DEFAULT_ASN_ORG)))
            .andExpect(jsonPath("$.[*].countryCode2").value(hasItem(DEFAULT_COUNTRY_CODE_2)))
            .andExpect(jsonPath("$.[*].countryCode3").value(hasItem(DEFAULT_COUNTRY_CODE_3)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())));
    }

    @Test
    @Transactional
    void getIpInfo() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get the ipInfo
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, ipInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ipInfo.getId().intValue()))
            .andExpect(jsonPath("$.asn").value(DEFAULT_ASN.intValue()))
            .andExpect(jsonPath("$.asnOrg").value(DEFAULT_ASN_ORG))
            .andExpect(jsonPath("$.countryCode2").value(DEFAULT_COUNTRY_CODE_2))
            .andExpect(jsonPath("$.countryCode3").value(DEFAULT_COUNTRY_CODE_3))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()));
    }

    @Test
    @Transactional
    void getIpInfosByIdFiltering() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        Long id = ipInfo.getId();

        defaultIpInfoShouldBeFound("id.equals=" + id);
        defaultIpInfoShouldNotBeFound("id.notEquals=" + id);

        defaultIpInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIpInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultIpInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIpInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn equals to DEFAULT_ASN
        defaultIpInfoShouldBeFound("asn.equals=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn equals to UPDATED_ASN
        defaultIpInfoShouldNotBeFound("asn.equals=" + UPDATED_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn not equals to DEFAULT_ASN
        defaultIpInfoShouldNotBeFound("asn.notEquals=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn not equals to UPDATED_ASN
        defaultIpInfoShouldBeFound("asn.notEquals=" + UPDATED_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn in DEFAULT_ASN or UPDATED_ASN
        defaultIpInfoShouldBeFound("asn.in=" + DEFAULT_ASN + "," + UPDATED_ASN);

        // Get all the ipInfoList where asn equals to UPDATED_ASN
        defaultIpInfoShouldNotBeFound("asn.in=" + UPDATED_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn is not null
        defaultIpInfoShouldBeFound("asn.specified=true");

        // Get all the ipInfoList where asn is null
        defaultIpInfoShouldNotBeFound("asn.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn is greater than or equal to DEFAULT_ASN
        defaultIpInfoShouldBeFound("asn.greaterThanOrEqual=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn is greater than or equal to UPDATED_ASN
        defaultIpInfoShouldNotBeFound("asn.greaterThanOrEqual=" + UPDATED_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn is less than or equal to DEFAULT_ASN
        defaultIpInfoShouldBeFound("asn.lessThanOrEqual=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn is less than or equal to SMALLER_ASN
        defaultIpInfoShouldNotBeFound("asn.lessThanOrEqual=" + SMALLER_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsLessThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn is less than DEFAULT_ASN
        defaultIpInfoShouldNotBeFound("asn.lessThan=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn is less than UPDATED_ASN
        defaultIpInfoShouldBeFound("asn.lessThan=" + UPDATED_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asn is greater than DEFAULT_ASN
        defaultIpInfoShouldNotBeFound("asn.greaterThan=" + DEFAULT_ASN);

        // Get all the ipInfoList where asn is greater than SMALLER_ASN
        defaultIpInfoShouldBeFound("asn.greaterThan=" + SMALLER_ASN);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg equals to DEFAULT_ASN_ORG
        defaultIpInfoShouldBeFound("asnOrg.equals=" + DEFAULT_ASN_ORG);

        // Get all the ipInfoList where asnOrg equals to UPDATED_ASN_ORG
        defaultIpInfoShouldNotBeFound("asnOrg.equals=" + UPDATED_ASN_ORG);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg not equals to DEFAULT_ASN_ORG
        defaultIpInfoShouldNotBeFound("asnOrg.notEquals=" + DEFAULT_ASN_ORG);

        // Get all the ipInfoList where asnOrg not equals to UPDATED_ASN_ORG
        defaultIpInfoShouldBeFound("asnOrg.notEquals=" + UPDATED_ASN_ORG);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgIsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg in DEFAULT_ASN_ORG or UPDATED_ASN_ORG
        defaultIpInfoShouldBeFound("asnOrg.in=" + DEFAULT_ASN_ORG + "," + UPDATED_ASN_ORG);

        // Get all the ipInfoList where asnOrg equals to UPDATED_ASN_ORG
        defaultIpInfoShouldNotBeFound("asnOrg.in=" + UPDATED_ASN_ORG);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg is not null
        defaultIpInfoShouldBeFound("asnOrg.specified=true");

        // Get all the ipInfoList where asnOrg is null
        defaultIpInfoShouldNotBeFound("asnOrg.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg contains DEFAULT_ASN_ORG
        defaultIpInfoShouldBeFound("asnOrg.contains=" + DEFAULT_ASN_ORG);

        // Get all the ipInfoList where asnOrg contains UPDATED_ASN_ORG
        defaultIpInfoShouldNotBeFound("asnOrg.contains=" + UPDATED_ASN_ORG);
    }

    @Test
    @Transactional
    void getAllIpInfosByAsnOrgNotContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where asnOrg does not contain DEFAULT_ASN_ORG
        defaultIpInfoShouldNotBeFound("asnOrg.doesNotContain=" + DEFAULT_ASN_ORG);

        // Get all the ipInfoList where asnOrg does not contain UPDATED_ASN_ORG
        defaultIpInfoShouldBeFound("asnOrg.doesNotContain=" + UPDATED_ASN_ORG);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2IsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 equals to DEFAULT_COUNTRY_CODE_2
        defaultIpInfoShouldBeFound("countryCode2.equals=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the ipInfoList where countryCode2 equals to UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldNotBeFound("countryCode2.equals=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 not equals to DEFAULT_COUNTRY_CODE_2
        defaultIpInfoShouldNotBeFound("countryCode2.notEquals=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the ipInfoList where countryCode2 not equals to UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldBeFound("countryCode2.notEquals=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2IsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 in DEFAULT_COUNTRY_CODE_2 or UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldBeFound("countryCode2.in=" + DEFAULT_COUNTRY_CODE_2 + "," + UPDATED_COUNTRY_CODE_2);

        // Get all the ipInfoList where countryCode2 equals to UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldNotBeFound("countryCode2.in=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2IsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 is not null
        defaultIpInfoShouldBeFound("countryCode2.specified=true");

        // Get all the ipInfoList where countryCode2 is null
        defaultIpInfoShouldNotBeFound("countryCode2.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2ContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 contains DEFAULT_COUNTRY_CODE_2
        defaultIpInfoShouldBeFound("countryCode2.contains=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the ipInfoList where countryCode2 contains UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldNotBeFound("countryCode2.contains=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode2NotContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode2 does not contain DEFAULT_COUNTRY_CODE_2
        defaultIpInfoShouldNotBeFound("countryCode2.doesNotContain=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the ipInfoList where countryCode2 does not contain UPDATED_COUNTRY_CODE_2
        defaultIpInfoShouldBeFound("countryCode2.doesNotContain=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3IsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 equals to DEFAULT_COUNTRY_CODE_3
        defaultIpInfoShouldBeFound("countryCode3.equals=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the ipInfoList where countryCode3 equals to UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldNotBeFound("countryCode3.equals=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 not equals to DEFAULT_COUNTRY_CODE_3
        defaultIpInfoShouldNotBeFound("countryCode3.notEquals=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the ipInfoList where countryCode3 not equals to UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldBeFound("countryCode3.notEquals=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3IsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 in DEFAULT_COUNTRY_CODE_3 or UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldBeFound("countryCode3.in=" + DEFAULT_COUNTRY_CODE_3 + "," + UPDATED_COUNTRY_CODE_3);

        // Get all the ipInfoList where countryCode3 equals to UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldNotBeFound("countryCode3.in=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3IsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 is not null
        defaultIpInfoShouldBeFound("countryCode3.specified=true");

        // Get all the ipInfoList where countryCode3 is null
        defaultIpInfoShouldNotBeFound("countryCode3.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3ContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 contains DEFAULT_COUNTRY_CODE_3
        defaultIpInfoShouldBeFound("countryCode3.contains=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the ipInfoList where countryCode3 contains UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldNotBeFound("countryCode3.contains=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllIpInfosByCountryCode3NotContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where countryCode3 does not contain DEFAULT_COUNTRY_CODE_3
        defaultIpInfoShouldNotBeFound("countryCode3.doesNotContain=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the ipInfoList where countryCode3 does not contain UPDATED_COUNTRY_CODE_3
        defaultIpInfoShouldBeFound("countryCode3.doesNotContain=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllIpInfosByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip equals to DEFAULT_IP
        defaultIpInfoShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the ipInfoList where ip equals to UPDATED_IP
        defaultIpInfoShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllIpInfosByIpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip not equals to DEFAULT_IP
        defaultIpInfoShouldNotBeFound("ip.notEquals=" + DEFAULT_IP);

        // Get all the ipInfoList where ip not equals to UPDATED_IP
        defaultIpInfoShouldBeFound("ip.notEquals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllIpInfosByIpIsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip in DEFAULT_IP or UPDATED_IP
        defaultIpInfoShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the ipInfoList where ip equals to UPDATED_IP
        defaultIpInfoShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllIpInfosByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip is not null
        defaultIpInfoShouldBeFound("ip.specified=true");

        // Get all the ipInfoList where ip is null
        defaultIpInfoShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByIpContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip contains DEFAULT_IP
        defaultIpInfoShouldBeFound("ip.contains=" + DEFAULT_IP);

        // Get all the ipInfoList where ip contains UPDATED_IP
        defaultIpInfoShouldNotBeFound("ip.contains=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllIpInfosByIpNotContainsSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where ip does not contain DEFAULT_IP
        defaultIpInfoShouldNotBeFound("ip.doesNotContain=" + DEFAULT_IP);

        // Get all the ipInfoList where ip does not contain UPDATED_IP
        defaultIpInfoShouldBeFound("ip.doesNotContain=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat equals to DEFAULT_LAT
        defaultIpInfoShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat equals to UPDATED_LAT
        defaultIpInfoShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat not equals to DEFAULT_LAT
        defaultIpInfoShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat not equals to UPDATED_LAT
        defaultIpInfoShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultIpInfoShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the ipInfoList where lat equals to UPDATED_LAT
        defaultIpInfoShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat is not null
        defaultIpInfoShouldBeFound("lat.specified=true");

        // Get all the ipInfoList where lat is null
        defaultIpInfoShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat is greater than or equal to DEFAULT_LAT
        defaultIpInfoShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat is greater than or equal to UPDATED_LAT
        defaultIpInfoShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat is less than or equal to DEFAULT_LAT
        defaultIpInfoShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat is less than or equal to SMALLER_LAT
        defaultIpInfoShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat is less than DEFAULT_LAT
        defaultIpInfoShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat is less than UPDATED_LAT
        defaultIpInfoShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lat is greater than DEFAULT_LAT
        defaultIpInfoShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the ipInfoList where lat is greater than SMALLER_LAT
        defaultIpInfoShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon equals to DEFAULT_LON
        defaultIpInfoShouldBeFound("lon.equals=" + DEFAULT_LON);

        // Get all the ipInfoList where lon equals to UPDATED_LON
        defaultIpInfoShouldNotBeFound("lon.equals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon not equals to DEFAULT_LON
        defaultIpInfoShouldNotBeFound("lon.notEquals=" + DEFAULT_LON);

        // Get all the ipInfoList where lon not equals to UPDATED_LON
        defaultIpInfoShouldBeFound("lon.notEquals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsInShouldWork() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon in DEFAULT_LON or UPDATED_LON
        defaultIpInfoShouldBeFound("lon.in=" + DEFAULT_LON + "," + UPDATED_LON);

        // Get all the ipInfoList where lon equals to UPDATED_LON
        defaultIpInfoShouldNotBeFound("lon.in=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon is not null
        defaultIpInfoShouldBeFound("lon.specified=true");

        // Get all the ipInfoList where lon is null
        defaultIpInfoShouldNotBeFound("lon.specified=false");
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon is greater than or equal to DEFAULT_LON
        defaultIpInfoShouldBeFound("lon.greaterThanOrEqual=" + DEFAULT_LON);

        // Get all the ipInfoList where lon is greater than or equal to UPDATED_LON
        defaultIpInfoShouldNotBeFound("lon.greaterThanOrEqual=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon is less than or equal to DEFAULT_LON
        defaultIpInfoShouldBeFound("lon.lessThanOrEqual=" + DEFAULT_LON);

        // Get all the ipInfoList where lon is less than or equal to SMALLER_LON
        defaultIpInfoShouldNotBeFound("lon.lessThanOrEqual=" + SMALLER_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsLessThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon is less than DEFAULT_LON
        defaultIpInfoShouldNotBeFound("lon.lessThan=" + DEFAULT_LON);

        // Get all the ipInfoList where lon is less than UPDATED_LON
        defaultIpInfoShouldBeFound("lon.lessThan=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        // Get all the ipInfoList where lon is greater than DEFAULT_LON
        defaultIpInfoShouldNotBeFound("lon.greaterThan=" + DEFAULT_LON);

        // Get all the ipInfoList where lon is greater than SMALLER_LON
        defaultIpInfoShouldBeFound("lon.greaterThan=" + SMALLER_LON);
    }

    @Test
    @Transactional
    void getAllIpInfosByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);
        Applicant applicant;
        if (TestUtil.findAll(em, Applicant.class).isEmpty()) {
            applicant = ApplicantResourceIT.createEntity(em);
            em.persist(applicant);
            em.flush();
        } else {
            applicant = TestUtil.findAll(em, Applicant.class).get(0);
        }
        em.persist(applicant);
        em.flush();
        ipInfo.setApplicant(applicant);
        ipInfoRepository.saveAndFlush(ipInfo);
        Long applicantId = applicant.getId();

        // Get all the ipInfoList where applicant equals to applicantId
        defaultIpInfoShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the ipInfoList where applicant equals to (applicantId + 1)
        defaultIpInfoShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIpInfoShouldBeFound(String filter) throws Exception {
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ipInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].asn").value(hasItem(DEFAULT_ASN.intValue())))
            .andExpect(jsonPath("$.[*].asnOrg").value(hasItem(DEFAULT_ASN_ORG)))
            .andExpect(jsonPath("$.[*].countryCode2").value(hasItem(DEFAULT_COUNTRY_CODE_2)))
            .andExpect(jsonPath("$.[*].countryCode3").value(hasItem(DEFAULT_COUNTRY_CODE_3)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())));

        // Check, that the count call also returns 1
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIpInfoShouldNotBeFound(String filter) throws Exception {
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIpInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIpInfo() throws Exception {
        // Get the ipInfo
        restIpInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIpInfo() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();

        // Update the ipInfo
        IpInfo updatedIpInfo = ipInfoRepository.findById(ipInfo.getId()).get();
        // Disconnect from session so that the updates on updatedIpInfo are not directly saved in db
        em.detach(updatedIpInfo);
        updatedIpInfo
            .asn(UPDATED_ASN)
            .asnOrg(UPDATED_ASN_ORG)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .ip(UPDATED_IP)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(updatedIpInfo);

        restIpInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ipInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
        IpInfo testIpInfo = ipInfoList.get(ipInfoList.size() - 1);
        assertThat(testIpInfo.getAsn()).isEqualTo(UPDATED_ASN);
        assertThat(testIpInfo.getAsnOrg()).isEqualTo(UPDATED_ASN_ORG);
        assertThat(testIpInfo.getCountryCode2()).isEqualTo(UPDATED_COUNTRY_CODE_2);
        assertThat(testIpInfo.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testIpInfo.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testIpInfo.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testIpInfo.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    void putNonExistingIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ipInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIpInfoWithPatch() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();

        // Update the ipInfo using partial update
        IpInfo partialUpdatedIpInfo = new IpInfo();
        partialUpdatedIpInfo.setId(ipInfo.getId());

        partialUpdatedIpInfo
            .asn(UPDATED_ASN)
            .asnOrg(UPDATED_ASN_ORG)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .ip(UPDATED_IP)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);

        restIpInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIpInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIpInfo))
            )
            .andExpect(status().isOk());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
        IpInfo testIpInfo = ipInfoList.get(ipInfoList.size() - 1);
        assertThat(testIpInfo.getAsn()).isEqualTo(UPDATED_ASN);
        assertThat(testIpInfo.getAsnOrg()).isEqualTo(UPDATED_ASN_ORG);
        assertThat(testIpInfo.getCountryCode2()).isEqualTo(DEFAULT_COUNTRY_CODE_2);
        assertThat(testIpInfo.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testIpInfo.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testIpInfo.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testIpInfo.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    void fullUpdateIpInfoWithPatch() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();

        // Update the ipInfo using partial update
        IpInfo partialUpdatedIpInfo = new IpInfo();
        partialUpdatedIpInfo.setId(ipInfo.getId());

        partialUpdatedIpInfo
            .asn(UPDATED_ASN)
            .asnOrg(UPDATED_ASN_ORG)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .ip(UPDATED_IP)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);

        restIpInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIpInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIpInfo))
            )
            .andExpect(status().isOk());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
        IpInfo testIpInfo = ipInfoList.get(ipInfoList.size() - 1);
        assertThat(testIpInfo.getAsn()).isEqualTo(UPDATED_ASN);
        assertThat(testIpInfo.getAsnOrg()).isEqualTo(UPDATED_ASN_ORG);
        assertThat(testIpInfo.getCountryCode2()).isEqualTo(UPDATED_COUNTRY_CODE_2);
        assertThat(testIpInfo.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testIpInfo.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testIpInfo.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testIpInfo.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    void patchNonExistingIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ipInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIpInfo() throws Exception {
        int databaseSizeBeforeUpdate = ipInfoRepository.findAll().size();
        ipInfo.setId(count.incrementAndGet());

        // Create the IpInfo
        IpInfoDTO ipInfoDTO = ipInfoMapper.toDto(ipInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ipInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IpInfo in the database
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIpInfo() throws Exception {
        // Initialize the database
        ipInfoRepository.saveAndFlush(ipInfo);

        int databaseSizeBeforeDelete = ipInfoRepository.findAll().size();

        // Delete the ipInfo
        restIpInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ipInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IpInfo> ipInfoList = ipInfoRepository.findAll();
        assertThat(ipInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
