package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.domain.UserAgentInfo;
import com.reactit.kyc.repository.UserAgentInfoRepository;
import com.reactit.kyc.service.criteria.UserAgentInfoCriteria;
import com.reactit.kyc.service.dto.UserAgentInfoDTO;
import com.reactit.kyc.service.mapper.UserAgentInfoMapper;
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
 * Integration tests for the {@link UserAgentInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserAgentInfoResourceIT {

    private static final String DEFAULT_UA_BROWSER = "AAAAAAAAAA";
    private static final String UPDATED_UA_BROWSER = "BBBBBBBBBB";

    private static final String DEFAULT_UA_BROWSER_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_UA_BROWSER_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_UA_DEVICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_UA_DEVICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UA_PLATFORM = "AAAAAAAAAA";
    private static final String UPDATED_UA_PLATFORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-agent-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAgentInfoRepository userAgentInfoRepository;

    @Autowired
    private UserAgentInfoMapper userAgentInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAgentInfoMockMvc;

    private UserAgentInfo userAgentInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAgentInfo createEntity(EntityManager em) {
        UserAgentInfo userAgentInfo = new UserAgentInfo()
            .uaBrowser(DEFAULT_UA_BROWSER)
            .uaBrowserVersion(DEFAULT_UA_BROWSER_VERSION)
            .uaDeviceType(DEFAULT_UA_DEVICE_TYPE)
            .uaPlatform(DEFAULT_UA_PLATFORM);
        return userAgentInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAgentInfo createUpdatedEntity(EntityManager em) {
        UserAgentInfo userAgentInfo = new UserAgentInfo()
            .uaBrowser(UPDATED_UA_BROWSER)
            .uaBrowserVersion(UPDATED_UA_BROWSER_VERSION)
            .uaDeviceType(UPDATED_UA_DEVICE_TYPE)
            .uaPlatform(UPDATED_UA_PLATFORM);
        return userAgentInfo;
    }

    @BeforeEach
    public void initTest() {
        userAgentInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAgentInfo() throws Exception {
        int databaseSizeBeforeCreate = userAgentInfoRepository.findAll().size();
        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);
        restUserAgentInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserAgentInfo testUserAgentInfo = userAgentInfoList.get(userAgentInfoList.size() - 1);
        assertThat(testUserAgentInfo.getUaBrowser()).isEqualTo(DEFAULT_UA_BROWSER);
        assertThat(testUserAgentInfo.getUaBrowserVersion()).isEqualTo(DEFAULT_UA_BROWSER_VERSION);
        assertThat(testUserAgentInfo.getUaDeviceType()).isEqualTo(DEFAULT_UA_DEVICE_TYPE);
        assertThat(testUserAgentInfo.getUaPlatform()).isEqualTo(DEFAULT_UA_PLATFORM);
    }

    @Test
    @Transactional
    void createUserAgentInfoWithExistingId() throws Exception {
        // Create the UserAgentInfo with an existing ID
        userAgentInfo.setId(1L);
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        int databaseSizeBeforeCreate = userAgentInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAgentInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserAgentInfos() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAgentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].uaBrowser").value(hasItem(DEFAULT_UA_BROWSER)))
            .andExpect(jsonPath("$.[*].uaBrowserVersion").value(hasItem(DEFAULT_UA_BROWSER_VERSION)))
            .andExpect(jsonPath("$.[*].uaDeviceType").value(hasItem(DEFAULT_UA_DEVICE_TYPE)))
            .andExpect(jsonPath("$.[*].uaPlatform").value(hasItem(DEFAULT_UA_PLATFORM)));
    }

    @Test
    @Transactional
    void getUserAgentInfo() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get the userAgentInfo
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userAgentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAgentInfo.getId().intValue()))
            .andExpect(jsonPath("$.uaBrowser").value(DEFAULT_UA_BROWSER))
            .andExpect(jsonPath("$.uaBrowserVersion").value(DEFAULT_UA_BROWSER_VERSION))
            .andExpect(jsonPath("$.uaDeviceType").value(DEFAULT_UA_DEVICE_TYPE))
            .andExpect(jsonPath("$.uaPlatform").value(DEFAULT_UA_PLATFORM));
    }

    @Test
    @Transactional
    void getUserAgentInfosByIdFiltering() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        Long id = userAgentInfo.getId();

        defaultUserAgentInfoShouldBeFound("id.equals=" + id);
        defaultUserAgentInfoShouldNotBeFound("id.notEquals=" + id);

        defaultUserAgentInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserAgentInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultUserAgentInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserAgentInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserIsEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser equals to DEFAULT_UA_BROWSER
        defaultUserAgentInfoShouldBeFound("uaBrowser.equals=" + DEFAULT_UA_BROWSER);

        // Get all the userAgentInfoList where uaBrowser equals to UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.equals=" + UPDATED_UA_BROWSER);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser not equals to DEFAULT_UA_BROWSER
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.notEquals=" + DEFAULT_UA_BROWSER);

        // Get all the userAgentInfoList where uaBrowser not equals to UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldBeFound("uaBrowser.notEquals=" + UPDATED_UA_BROWSER);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserIsInShouldWork() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser in DEFAULT_UA_BROWSER or UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldBeFound("uaBrowser.in=" + DEFAULT_UA_BROWSER + "," + UPDATED_UA_BROWSER);

        // Get all the userAgentInfoList where uaBrowser equals to UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.in=" + UPDATED_UA_BROWSER);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser is not null
        defaultUserAgentInfoShouldBeFound("uaBrowser.specified=true");

        // Get all the userAgentInfoList where uaBrowser is null
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser contains DEFAULT_UA_BROWSER
        defaultUserAgentInfoShouldBeFound("uaBrowser.contains=" + DEFAULT_UA_BROWSER);

        // Get all the userAgentInfoList where uaBrowser contains UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.contains=" + UPDATED_UA_BROWSER);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserNotContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowser does not contain DEFAULT_UA_BROWSER
        defaultUserAgentInfoShouldNotBeFound("uaBrowser.doesNotContain=" + DEFAULT_UA_BROWSER);

        // Get all the userAgentInfoList where uaBrowser does not contain UPDATED_UA_BROWSER
        defaultUserAgentInfoShouldBeFound("uaBrowser.doesNotContain=" + UPDATED_UA_BROWSER);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion equals to DEFAULT_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.equals=" + DEFAULT_UA_BROWSER_VERSION);

        // Get all the userAgentInfoList where uaBrowserVersion equals to UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.equals=" + UPDATED_UA_BROWSER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion not equals to DEFAULT_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.notEquals=" + DEFAULT_UA_BROWSER_VERSION);

        // Get all the userAgentInfoList where uaBrowserVersion not equals to UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.notEquals=" + UPDATED_UA_BROWSER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion in DEFAULT_UA_BROWSER_VERSION or UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.in=" + DEFAULT_UA_BROWSER_VERSION + "," + UPDATED_UA_BROWSER_VERSION);

        // Get all the userAgentInfoList where uaBrowserVersion equals to UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.in=" + UPDATED_UA_BROWSER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion is not null
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.specified=true");

        // Get all the userAgentInfoList where uaBrowserVersion is null
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion contains DEFAULT_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.contains=" + DEFAULT_UA_BROWSER_VERSION);

        // Get all the userAgentInfoList where uaBrowserVersion contains UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.contains=" + UPDATED_UA_BROWSER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaBrowserVersionNotContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaBrowserVersion does not contain DEFAULT_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldNotBeFound("uaBrowserVersion.doesNotContain=" + DEFAULT_UA_BROWSER_VERSION);

        // Get all the userAgentInfoList where uaBrowserVersion does not contain UPDATED_UA_BROWSER_VERSION
        defaultUserAgentInfoShouldBeFound("uaBrowserVersion.doesNotContain=" + UPDATED_UA_BROWSER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType equals to DEFAULT_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldBeFound("uaDeviceType.equals=" + DEFAULT_UA_DEVICE_TYPE);

        // Get all the userAgentInfoList where uaDeviceType equals to UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.equals=" + UPDATED_UA_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType not equals to DEFAULT_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.notEquals=" + DEFAULT_UA_DEVICE_TYPE);

        // Get all the userAgentInfoList where uaDeviceType not equals to UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldBeFound("uaDeviceType.notEquals=" + UPDATED_UA_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType in DEFAULT_UA_DEVICE_TYPE or UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldBeFound("uaDeviceType.in=" + DEFAULT_UA_DEVICE_TYPE + "," + UPDATED_UA_DEVICE_TYPE);

        // Get all the userAgentInfoList where uaDeviceType equals to UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.in=" + UPDATED_UA_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType is not null
        defaultUserAgentInfoShouldBeFound("uaDeviceType.specified=true");

        // Get all the userAgentInfoList where uaDeviceType is null
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType contains DEFAULT_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldBeFound("uaDeviceType.contains=" + DEFAULT_UA_DEVICE_TYPE);

        // Get all the userAgentInfoList where uaDeviceType contains UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.contains=" + UPDATED_UA_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaDeviceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaDeviceType does not contain DEFAULT_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldNotBeFound("uaDeviceType.doesNotContain=" + DEFAULT_UA_DEVICE_TYPE);

        // Get all the userAgentInfoList where uaDeviceType does not contain UPDATED_UA_DEVICE_TYPE
        defaultUserAgentInfoShouldBeFound("uaDeviceType.doesNotContain=" + UPDATED_UA_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformIsEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform equals to DEFAULT_UA_PLATFORM
        defaultUserAgentInfoShouldBeFound("uaPlatform.equals=" + DEFAULT_UA_PLATFORM);

        // Get all the userAgentInfoList where uaPlatform equals to UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.equals=" + UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform not equals to DEFAULT_UA_PLATFORM
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.notEquals=" + DEFAULT_UA_PLATFORM);

        // Get all the userAgentInfoList where uaPlatform not equals to UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldBeFound("uaPlatform.notEquals=" + UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformIsInShouldWork() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform in DEFAULT_UA_PLATFORM or UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldBeFound("uaPlatform.in=" + DEFAULT_UA_PLATFORM + "," + UPDATED_UA_PLATFORM);

        // Get all the userAgentInfoList where uaPlatform equals to UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.in=" + UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform is not null
        defaultUserAgentInfoShouldBeFound("uaPlatform.specified=true");

        // Get all the userAgentInfoList where uaPlatform is null
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform contains DEFAULT_UA_PLATFORM
        defaultUserAgentInfoShouldBeFound("uaPlatform.contains=" + DEFAULT_UA_PLATFORM);

        // Get all the userAgentInfoList where uaPlatform contains UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.contains=" + UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByUaPlatformNotContainsSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        // Get all the userAgentInfoList where uaPlatform does not contain DEFAULT_UA_PLATFORM
        defaultUserAgentInfoShouldNotBeFound("uaPlatform.doesNotContain=" + DEFAULT_UA_PLATFORM);

        // Get all the userAgentInfoList where uaPlatform does not contain UPDATED_UA_PLATFORM
        defaultUserAgentInfoShouldBeFound("uaPlatform.doesNotContain=" + UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void getAllUserAgentInfosByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);
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
        userAgentInfo.setApplicant(applicant);
        userAgentInfoRepository.saveAndFlush(userAgentInfo);
        Long applicantId = applicant.getId();

        // Get all the userAgentInfoList where applicant equals to applicantId
        defaultUserAgentInfoShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the userAgentInfoList where applicant equals to (applicantId + 1)
        defaultUserAgentInfoShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserAgentInfoShouldBeFound(String filter) throws Exception {
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAgentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].uaBrowser").value(hasItem(DEFAULT_UA_BROWSER)))
            .andExpect(jsonPath("$.[*].uaBrowserVersion").value(hasItem(DEFAULT_UA_BROWSER_VERSION)))
            .andExpect(jsonPath("$.[*].uaDeviceType").value(hasItem(DEFAULT_UA_DEVICE_TYPE)))
            .andExpect(jsonPath("$.[*].uaPlatform").value(hasItem(DEFAULT_UA_PLATFORM)));

        // Check, that the count call also returns 1
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserAgentInfoShouldNotBeFound(String filter) throws Exception {
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserAgentInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserAgentInfo() throws Exception {
        // Get the userAgentInfo
        restUserAgentInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserAgentInfo() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();

        // Update the userAgentInfo
        UserAgentInfo updatedUserAgentInfo = userAgentInfoRepository.findById(userAgentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserAgentInfo are not directly saved in db
        em.detach(updatedUserAgentInfo);
        updatedUserAgentInfo
            .uaBrowser(UPDATED_UA_BROWSER)
            .uaBrowserVersion(UPDATED_UA_BROWSER_VERSION)
            .uaDeviceType(UPDATED_UA_DEVICE_TYPE)
            .uaPlatform(UPDATED_UA_PLATFORM);
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(updatedUserAgentInfo);

        restUserAgentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAgentInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
        UserAgentInfo testUserAgentInfo = userAgentInfoList.get(userAgentInfoList.size() - 1);
        assertThat(testUserAgentInfo.getUaBrowser()).isEqualTo(UPDATED_UA_BROWSER);
        assertThat(testUserAgentInfo.getUaBrowserVersion()).isEqualTo(UPDATED_UA_BROWSER_VERSION);
        assertThat(testUserAgentInfo.getUaDeviceType()).isEqualTo(UPDATED_UA_DEVICE_TYPE);
        assertThat(testUserAgentInfo.getUaPlatform()).isEqualTo(UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void putNonExistingUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAgentInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAgentInfoWithPatch() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();

        // Update the userAgentInfo using partial update
        UserAgentInfo partialUpdatedUserAgentInfo = new UserAgentInfo();
        partialUpdatedUserAgentInfo.setId(userAgentInfo.getId());

        partialUpdatedUserAgentInfo.uaBrowser(UPDATED_UA_BROWSER).uaDeviceType(UPDATED_UA_DEVICE_TYPE);

        restUserAgentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAgentInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAgentInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
        UserAgentInfo testUserAgentInfo = userAgentInfoList.get(userAgentInfoList.size() - 1);
        assertThat(testUserAgentInfo.getUaBrowser()).isEqualTo(UPDATED_UA_BROWSER);
        assertThat(testUserAgentInfo.getUaBrowserVersion()).isEqualTo(DEFAULT_UA_BROWSER_VERSION);
        assertThat(testUserAgentInfo.getUaDeviceType()).isEqualTo(UPDATED_UA_DEVICE_TYPE);
        assertThat(testUserAgentInfo.getUaPlatform()).isEqualTo(DEFAULT_UA_PLATFORM);
    }

    @Test
    @Transactional
    void fullUpdateUserAgentInfoWithPatch() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();

        // Update the userAgentInfo using partial update
        UserAgentInfo partialUpdatedUserAgentInfo = new UserAgentInfo();
        partialUpdatedUserAgentInfo.setId(userAgentInfo.getId());

        partialUpdatedUserAgentInfo
            .uaBrowser(UPDATED_UA_BROWSER)
            .uaBrowserVersion(UPDATED_UA_BROWSER_VERSION)
            .uaDeviceType(UPDATED_UA_DEVICE_TYPE)
            .uaPlatform(UPDATED_UA_PLATFORM);

        restUserAgentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAgentInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAgentInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
        UserAgentInfo testUserAgentInfo = userAgentInfoList.get(userAgentInfoList.size() - 1);
        assertThat(testUserAgentInfo.getUaBrowser()).isEqualTo(UPDATED_UA_BROWSER);
        assertThat(testUserAgentInfo.getUaBrowserVersion()).isEqualTo(UPDATED_UA_BROWSER_VERSION);
        assertThat(testUserAgentInfo.getUaDeviceType()).isEqualTo(UPDATED_UA_DEVICE_TYPE);
        assertThat(testUserAgentInfo.getUaPlatform()).isEqualTo(UPDATED_UA_PLATFORM);
    }

    @Test
    @Transactional
    void patchNonExistingUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAgentInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = userAgentInfoRepository.findAll().size();
        userAgentInfo.setId(count.incrementAndGet());

        // Create the UserAgentInfo
        UserAgentInfoDTO userAgentInfoDTO = userAgentInfoMapper.toDto(userAgentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAgentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAgentInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAgentInfo in the database
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAgentInfo() throws Exception {
        // Initialize the database
        userAgentInfoRepository.saveAndFlush(userAgentInfo);

        int databaseSizeBeforeDelete = userAgentInfoRepository.findAll().size();

        // Delete the userAgentInfo
        restUserAgentInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAgentInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAgentInfo> userAgentInfoList = userAgentInfoRepository.findAll();
        assertThat(userAgentInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
