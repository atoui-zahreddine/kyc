package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.domain.IpInfo;
import com.reactit.kyc.domain.UserAgentInfo;
import com.reactit.kyc.domain.enumeration.Platform;
import com.reactit.kyc.repository.ApplicantRepository;
import com.reactit.kyc.service.criteria.ApplicantCriteria;
import com.reactit.kyc.service.dto.ApplicantDTO;
import com.reactit.kyc.service.mapper.ApplicantMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ApplicantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;
    private static final Long SMALLER_CREATED_BY = 1L - 1L;

    private static final Instant DEFAULT_MODIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Platform DEFAULT_PLATFORM = Platform.API;
    private static final Platform UPDATED_PLATFORM = Platform.WEB;

    private static final String ENTITY_API_URL = "/api/applicants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantMapper applicantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT)
            .platform(DEFAULT_PLATFORM);
        return applicant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createUpdatedEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .platform(UPDATED_PLATFORM);
        return applicant;
    }

    @BeforeEach
    public void initTest() {
        applicant = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();
        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);
        restApplicantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApplicant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicant.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testApplicant.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
    }

    @Test
    @Transactional
    void createApplicantWithExistingId() throws Exception {
        // Create the Applicant with an existing ID
        applicant.setId(1L);
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }

    @Test
    @Transactional
    void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get the applicant
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL_ID, applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicant.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()));
    }

    @Test
    @Transactional
    void getApplicantsByIdFiltering() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        Long id = applicant.getId();

        defaultApplicantShouldBeFound("id.equals=" + id);
        defaultApplicantShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdAt equals to DEFAULT_CREATED_AT
        defaultApplicantShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the applicantList where createdAt equals to UPDATED_CREATED_AT
        defaultApplicantShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdAt not equals to DEFAULT_CREATED_AT
        defaultApplicantShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the applicantList where createdAt not equals to UPDATED_CREATED_AT
        defaultApplicantShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultApplicantShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the applicantList where createdAt equals to UPDATED_CREATED_AT
        defaultApplicantShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdAt is not null
        defaultApplicantShouldBeFound("createdAt.specified=true");

        // Get all the applicantList where createdAt is null
        defaultApplicantShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicantList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy is not null
        defaultApplicantShouldBeFound("createdBy.specified=true");

        // Get all the applicantList where createdBy is null
        defaultApplicantShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy is less than DEFAULT_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy is less than UPDATED_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where createdBy is greater than DEFAULT_CREATED_BY
        defaultApplicantShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the applicantList where createdBy is greater than SMALLER_CREATED_BY
        defaultApplicantShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantsByModifiedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where modifiedAt equals to DEFAULT_MODIFIED_AT
        defaultApplicantShouldBeFound("modifiedAt.equals=" + DEFAULT_MODIFIED_AT);

        // Get all the applicantList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultApplicantShouldNotBeFound("modifiedAt.equals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByModifiedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where modifiedAt not equals to DEFAULT_MODIFIED_AT
        defaultApplicantShouldNotBeFound("modifiedAt.notEquals=" + DEFAULT_MODIFIED_AT);

        // Get all the applicantList where modifiedAt not equals to UPDATED_MODIFIED_AT
        defaultApplicantShouldBeFound("modifiedAt.notEquals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByModifiedAtIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where modifiedAt in DEFAULT_MODIFIED_AT or UPDATED_MODIFIED_AT
        defaultApplicantShouldBeFound("modifiedAt.in=" + DEFAULT_MODIFIED_AT + "," + UPDATED_MODIFIED_AT);

        // Get all the applicantList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultApplicantShouldNotBeFound("modifiedAt.in=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantsByModifiedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where modifiedAt is not null
        defaultApplicantShouldBeFound("modifiedAt.specified=true");

        // Get all the applicantList where modifiedAt is null
        defaultApplicantShouldNotBeFound("modifiedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantsByPlatformIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where platform equals to DEFAULT_PLATFORM
        defaultApplicantShouldBeFound("platform.equals=" + DEFAULT_PLATFORM);

        // Get all the applicantList where platform equals to UPDATED_PLATFORM
        defaultApplicantShouldNotBeFound("platform.equals=" + UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void getAllApplicantsByPlatformIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where platform not equals to DEFAULT_PLATFORM
        defaultApplicantShouldNotBeFound("platform.notEquals=" + DEFAULT_PLATFORM);

        // Get all the applicantList where platform not equals to UPDATED_PLATFORM
        defaultApplicantShouldBeFound("platform.notEquals=" + UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void getAllApplicantsByPlatformIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where platform in DEFAULT_PLATFORM or UPDATED_PLATFORM
        defaultApplicantShouldBeFound("platform.in=" + DEFAULT_PLATFORM + "," + UPDATED_PLATFORM);

        // Get all the applicantList where platform equals to UPDATED_PLATFORM
        defaultApplicantShouldNotBeFound("platform.in=" + UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void getAllApplicantsByPlatformIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where platform is not null
        defaultApplicantShouldBeFound("platform.specified=true");

        // Get all the applicantList where platform is null
        defaultApplicantShouldNotBeFound("platform.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantsByApplicantLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        ApplicantLevel applicantLevel;
        if (TestUtil.findAll(em, ApplicantLevel.class).isEmpty()) {
            applicantLevel = ApplicantLevelResourceIT.createEntity(em);
            em.persist(applicantLevel);
            em.flush();
        } else {
            applicantLevel = TestUtil.findAll(em, ApplicantLevel.class).get(0);
        }
        em.persist(applicantLevel);
        em.flush();
        applicant.setApplicantLevel(applicantLevel);
        applicantRepository.saveAndFlush(applicant);
        Long applicantLevelId = applicantLevel.getId();

        // Get all the applicantList where applicantLevel equals to applicantLevelId
        defaultApplicantShouldBeFound("applicantLevelId.equals=" + applicantLevelId);

        // Get all the applicantList where applicantLevel equals to (applicantLevelId + 1)
        defaultApplicantShouldNotBeFound("applicantLevelId.equals=" + (applicantLevelId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantsByApplicantInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        ApplicantInfo applicantInfo;
        if (TestUtil.findAll(em, ApplicantInfo.class).isEmpty()) {
            applicantInfo = ApplicantInfoResourceIT.createEntity(em);
            em.persist(applicantInfo);
            em.flush();
        } else {
            applicantInfo = TestUtil.findAll(em, ApplicantInfo.class).get(0);
        }
        em.persist(applicantInfo);
        em.flush();
        applicant.setApplicantInfo(applicantInfo);
        applicantInfo.setApplicant(applicant);
        applicantRepository.saveAndFlush(applicant);
        Long applicantInfoId = applicantInfo.getId();

        // Get all the applicantList where applicantInfo equals to applicantInfoId
        defaultApplicantShouldBeFound("applicantInfoId.equals=" + applicantInfoId);

        // Get all the applicantList where applicantInfo equals to (applicantInfoId + 1)
        defaultApplicantShouldNotBeFound("applicantInfoId.equals=" + (applicantInfoId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantsByIpInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        IpInfo ipInfo;
        if (TestUtil.findAll(em, IpInfo.class).isEmpty()) {
            ipInfo = IpInfoResourceIT.createEntity(em);
            em.persist(ipInfo);
            em.flush();
        } else {
            ipInfo = TestUtil.findAll(em, IpInfo.class).get(0);
        }
        em.persist(ipInfo);
        em.flush();
        applicant.setIpInfo(ipInfo);
        ipInfo.setApplicant(applicant);
        applicantRepository.saveAndFlush(applicant);
        Long ipInfoId = ipInfo.getId();

        // Get all the applicantList where ipInfo equals to ipInfoId
        defaultApplicantShouldBeFound("ipInfoId.equals=" + ipInfoId);

        // Get all the applicantList where ipInfo equals to (ipInfoId + 1)
        defaultApplicantShouldNotBeFound("ipInfoId.equals=" + (ipInfoId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantsByUserAgentInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        UserAgentInfo userAgentInfo;
        if (TestUtil.findAll(em, UserAgentInfo.class).isEmpty()) {
            userAgentInfo = UserAgentInfoResourceIT.createEntity(em);
            em.persist(userAgentInfo);
            em.flush();
        } else {
            userAgentInfo = TestUtil.findAll(em, UserAgentInfo.class).get(0);
        }
        em.persist(userAgentInfo);
        em.flush();
        applicant.setUserAgentInfo(userAgentInfo);
        userAgentInfo.setApplicant(applicant);
        applicantRepository.saveAndFlush(applicant);
        Long userAgentInfoId = userAgentInfo.getId();

        // Get all the applicantList where userAgentInfo equals to userAgentInfoId
        defaultApplicantShouldBeFound("userAgentInfoId.equals=" + userAgentInfoId);

        // Get all the applicantList where userAgentInfo equals to (userAgentInfoId + 1)
        defaultApplicantShouldNotBeFound("userAgentInfoId.equals=" + (userAgentInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantShouldBeFound(String filter) throws Exception {
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));

        // Check, that the count call also returns 1
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantShouldNotBeFound(String filter) throws Exception {
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        Applicant updatedApplicant = applicantRepository.findById(applicant.getId()).get();
        // Disconnect from session so that the updates on updatedApplicant are not directly saved in db
        em.detach(updatedApplicant);
        updatedApplicant
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .platform(UPDATED_PLATFORM);
        ApplicantDTO applicantDTO = applicantMapper.toDto(updatedApplicant);

        restApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplicant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicant.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testApplicant.getPlatform()).isEqualTo(UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void putNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantWithPatch() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant using partial update
        Applicant partialUpdatedApplicant = new Applicant();
        partialUpdatedApplicant.setId(applicant.getId());

        partialUpdatedApplicant.createdBy(UPDATED_CREATED_BY).platform(UPDATED_PLATFORM);

        restApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicant))
            )
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApplicant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicant.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testApplicant.getPlatform()).isEqualTo(UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void fullUpdateApplicantWithPatch() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant using partial update
        Applicant partialUpdatedApplicant = new Applicant();
        partialUpdatedApplicant.setId(applicant.getId());

        partialUpdatedApplicant
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .platform(UPDATED_PLATFORM);

        restApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicant))
            )
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplicant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicant.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testApplicant.getPlatform()).isEqualTo(UPDATED_PLATFORM);
    }

    @Test
    @Transactional
    void patchNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();
        applicant.setId(count.incrementAndGet());

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(applicantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Delete the applicant
        restApplicantMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
