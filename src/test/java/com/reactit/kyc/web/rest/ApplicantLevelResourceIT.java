package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.domain.Step;
import com.reactit.kyc.repository.ApplicantLevelRepository;
import com.reactit.kyc.service.ApplicantLevelService;
import com.reactit.kyc.service.criteria.ApplicantLevelCriteria;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import com.reactit.kyc.service.mapper.ApplicantLevelMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicantLevelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantLevelResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;
    private static final Long SMALLER_CREATED_BY = 1L - 1L;

    private static final Instant DEFAULT_MODIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/applicant-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantLevelRepository applicantLevelRepository;

    @Mock
    private ApplicantLevelRepository applicantLevelRepositoryMock;

    @Autowired
    private ApplicantLevelMapper applicantLevelMapper;

    @Mock
    private ApplicantLevelService applicantLevelServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantLevelMockMvc;

    private ApplicantLevel applicantLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantLevel createEntity(EntityManager em) {
        ApplicantLevel applicantLevel = new ApplicantLevel()
            .code(DEFAULT_CODE)
            .levelName(DEFAULT_LEVEL_NAME)
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return applicantLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantLevel createUpdatedEntity(EntityManager em) {
        ApplicantLevel applicantLevel = new ApplicantLevel()
            .code(UPDATED_CODE)
            .levelName(UPDATED_LEVEL_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return applicantLevel;
    }

    @BeforeEach
    public void initTest() {
        applicantLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicantLevel() throws Exception {
        int databaseSizeBeforeCreate = applicantLevelRepository.findAll().size();
        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);
        restApplicantLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantLevel testApplicantLevel = applicantLevelList.get(applicantLevelList.size() - 1);
        assertThat(testApplicantLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicantLevel.getLevelName()).isEqualTo(DEFAULT_LEVEL_NAME);
        assertThat(testApplicantLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicantLevel.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testApplicantLevel.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApplicantLevel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicantLevel.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createApplicantLevelWithExistingId() throws Exception {
        // Create the ApplicantLevel with an existing ID
        applicantLevel.setId(1L);
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        int databaseSizeBeforeCreate = applicantLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicantLevels() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].levelName").value(hasItem(DEFAULT_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicantLevelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicantLevelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicantLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicantLevelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicantLevelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicantLevelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicantLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicantLevelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getApplicantLevel() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get the applicantLevel
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, applicantLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantLevel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.levelName").value(DEFAULT_LEVEL_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getApplicantLevelsByIdFiltering() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        Long id = applicantLevel.getId();

        defaultApplicantLevelShouldBeFound("id.equals=" + id);
        defaultApplicantLevelShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantLevelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantLevelShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantLevelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantLevelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code equals to DEFAULT_CODE
        defaultApplicantLevelShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the applicantLevelList where code equals to UPDATED_CODE
        defaultApplicantLevelShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code not equals to DEFAULT_CODE
        defaultApplicantLevelShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the applicantLevelList where code not equals to UPDATED_CODE
        defaultApplicantLevelShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code in DEFAULT_CODE or UPDATED_CODE
        defaultApplicantLevelShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the applicantLevelList where code equals to UPDATED_CODE
        defaultApplicantLevelShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code is not null
        defaultApplicantLevelShouldBeFound("code.specified=true");

        // Get all the applicantLevelList where code is null
        defaultApplicantLevelShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code contains DEFAULT_CODE
        defaultApplicantLevelShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the applicantLevelList where code contains UPDATED_CODE
        defaultApplicantLevelShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where code does not contain DEFAULT_CODE
        defaultApplicantLevelShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the applicantLevelList where code does not contain UPDATED_CODE
        defaultApplicantLevelShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName equals to DEFAULT_LEVEL_NAME
        defaultApplicantLevelShouldBeFound("levelName.equals=" + DEFAULT_LEVEL_NAME);

        // Get all the applicantLevelList where levelName equals to UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldNotBeFound("levelName.equals=" + UPDATED_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName not equals to DEFAULT_LEVEL_NAME
        defaultApplicantLevelShouldNotBeFound("levelName.notEquals=" + DEFAULT_LEVEL_NAME);

        // Get all the applicantLevelList where levelName not equals to UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldBeFound("levelName.notEquals=" + UPDATED_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName in DEFAULT_LEVEL_NAME or UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldBeFound("levelName.in=" + DEFAULT_LEVEL_NAME + "," + UPDATED_LEVEL_NAME);

        // Get all the applicantLevelList where levelName equals to UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldNotBeFound("levelName.in=" + UPDATED_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName is not null
        defaultApplicantLevelShouldBeFound("levelName.specified=true");

        // Get all the applicantLevelList where levelName is null
        defaultApplicantLevelShouldNotBeFound("levelName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName contains DEFAULT_LEVEL_NAME
        defaultApplicantLevelShouldBeFound("levelName.contains=" + DEFAULT_LEVEL_NAME);

        // Get all the applicantLevelList where levelName contains UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldNotBeFound("levelName.contains=" + UPDATED_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByLevelNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where levelName does not contain DEFAULT_LEVEL_NAME
        defaultApplicantLevelShouldNotBeFound("levelName.doesNotContain=" + DEFAULT_LEVEL_NAME);

        // Get all the applicantLevelList where levelName does not contain UPDATED_LEVEL_NAME
        defaultApplicantLevelShouldBeFound("levelName.doesNotContain=" + UPDATED_LEVEL_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description equals to DEFAULT_DESCRIPTION
        defaultApplicantLevelShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the applicantLevelList where description equals to UPDATED_DESCRIPTION
        defaultApplicantLevelShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description not equals to DEFAULT_DESCRIPTION
        defaultApplicantLevelShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the applicantLevelList where description not equals to UPDATED_DESCRIPTION
        defaultApplicantLevelShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApplicantLevelShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the applicantLevelList where description equals to UPDATED_DESCRIPTION
        defaultApplicantLevelShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description is not null
        defaultApplicantLevelShouldBeFound("description.specified=true");

        // Get all the applicantLevelList where description is null
        defaultApplicantLevelShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description contains DEFAULT_DESCRIPTION
        defaultApplicantLevelShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the applicantLevelList where description contains UPDATED_DESCRIPTION
        defaultApplicantLevelShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where description does not contain DEFAULT_DESCRIPTION
        defaultApplicantLevelShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the applicantLevelList where description does not contain UPDATED_DESCRIPTION
        defaultApplicantLevelShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url equals to DEFAULT_URL
        defaultApplicantLevelShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the applicantLevelList where url equals to UPDATED_URL
        defaultApplicantLevelShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url not equals to DEFAULT_URL
        defaultApplicantLevelShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the applicantLevelList where url not equals to UPDATED_URL
        defaultApplicantLevelShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url in DEFAULT_URL or UPDATED_URL
        defaultApplicantLevelShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the applicantLevelList where url equals to UPDATED_URL
        defaultApplicantLevelShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url is not null
        defaultApplicantLevelShouldBeFound("url.specified=true");

        // Get all the applicantLevelList where url is null
        defaultApplicantLevelShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url contains DEFAULT_URL
        defaultApplicantLevelShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the applicantLevelList where url contains UPDATED_URL
        defaultApplicantLevelShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where url does not contain DEFAULT_URL
        defaultApplicantLevelShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the applicantLevelList where url does not contain UPDATED_URL
        defaultApplicantLevelShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdAt equals to DEFAULT_CREATED_AT
        defaultApplicantLevelShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the applicantLevelList where createdAt equals to UPDATED_CREATED_AT
        defaultApplicantLevelShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdAt not equals to DEFAULT_CREATED_AT
        defaultApplicantLevelShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the applicantLevelList where createdAt not equals to UPDATED_CREATED_AT
        defaultApplicantLevelShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultApplicantLevelShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the applicantLevelList where createdAt equals to UPDATED_CREATED_AT
        defaultApplicantLevelShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdAt is not null
        defaultApplicantLevelShouldBeFound("createdAt.specified=true");

        // Get all the applicantLevelList where createdAt is null
        defaultApplicantLevelShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicantLevelList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy is not null
        defaultApplicantLevelShouldBeFound("createdBy.specified=true");

        // Get all the applicantLevelList where createdBy is null
        defaultApplicantLevelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy is less than DEFAULT_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy is less than UPDATED_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where createdBy is greater than DEFAULT_CREATED_BY
        defaultApplicantLevelShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the applicantLevelList where createdBy is greater than SMALLER_CREATED_BY
        defaultApplicantLevelShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByModifiedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where modifiedAt equals to DEFAULT_MODIFIED_AT
        defaultApplicantLevelShouldBeFound("modifiedAt.equals=" + DEFAULT_MODIFIED_AT);

        // Get all the applicantLevelList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultApplicantLevelShouldNotBeFound("modifiedAt.equals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByModifiedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where modifiedAt not equals to DEFAULT_MODIFIED_AT
        defaultApplicantLevelShouldNotBeFound("modifiedAt.notEquals=" + DEFAULT_MODIFIED_AT);

        // Get all the applicantLevelList where modifiedAt not equals to UPDATED_MODIFIED_AT
        defaultApplicantLevelShouldBeFound("modifiedAt.notEquals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByModifiedAtIsInShouldWork() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where modifiedAt in DEFAULT_MODIFIED_AT or UPDATED_MODIFIED_AT
        defaultApplicantLevelShouldBeFound("modifiedAt.in=" + DEFAULT_MODIFIED_AT + "," + UPDATED_MODIFIED_AT);

        // Get all the applicantLevelList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultApplicantLevelShouldNotBeFound("modifiedAt.in=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByModifiedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        // Get all the applicantLevelList where modifiedAt is not null
        defaultApplicantLevelShouldBeFound("modifiedAt.specified=true");

        // Get all the applicantLevelList where modifiedAt is null
        defaultApplicantLevelShouldNotBeFound("modifiedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByStepIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);
        Step step;
        if (TestUtil.findAll(em, Step.class).isEmpty()) {
            step = StepResourceIT.createEntity(em);
            em.persist(step);
            em.flush();
        } else {
            step = TestUtil.findAll(em, Step.class).get(0);
        }
        em.persist(step);
        em.flush();
        applicantLevel.addStep(step);
        applicantLevelRepository.saveAndFlush(applicantLevel);
        Long stepId = step.getId();

        // Get all the applicantLevelList where step equals to stepId
        defaultApplicantLevelShouldBeFound("stepId.equals=" + stepId);

        // Get all the applicantLevelList where step equals to (stepId + 1)
        defaultApplicantLevelShouldNotBeFound("stepId.equals=" + (stepId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantLevelsByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);
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
        applicantLevel.addApplicant(applicant);
        applicantLevelRepository.saveAndFlush(applicantLevel);
        Long applicantId = applicant.getId();

        // Get all the applicantLevelList where applicant equals to applicantId
        defaultApplicantLevelShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the applicantLevelList where applicant equals to (applicantId + 1)
        defaultApplicantLevelShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantLevelShouldBeFound(String filter) throws Exception {
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].levelName").value(hasItem(DEFAULT_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));

        // Check, that the count call also returns 1
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantLevelShouldNotBeFound(String filter) throws Exception {
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicantLevel() throws Exception {
        // Get the applicantLevel
        restApplicantLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicantLevel() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();

        // Update the applicantLevel
        ApplicantLevel updatedApplicantLevel = applicantLevelRepository.findById(applicantLevel.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantLevel are not directly saved in db
        em.detach(updatedApplicantLevel);
        updatedApplicantLevel
            .code(UPDATED_CODE)
            .levelName(UPDATED_LEVEL_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(updatedApplicantLevel);

        restApplicantLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
        ApplicantLevel testApplicantLevel = applicantLevelList.get(applicantLevelList.size() - 1);
        assertThat(testApplicantLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicantLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testApplicantLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicantLevel.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testApplicantLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplicantLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicantLevel.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantLevelWithPatch() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();

        // Update the applicantLevel using partial update
        ApplicantLevel partialUpdatedApplicantLevel = new ApplicantLevel();
        partialUpdatedApplicantLevel.setId(applicantLevel.getId());

        partialUpdatedApplicantLevel
            .levelName(UPDATED_LEVEL_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restApplicantLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantLevel))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
        ApplicantLevel testApplicantLevel = applicantLevelList.get(applicantLevelList.size() - 1);
        assertThat(testApplicantLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicantLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testApplicantLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicantLevel.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testApplicantLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplicantLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicantLevel.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateApplicantLevelWithPatch() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();

        // Update the applicantLevel using partial update
        ApplicantLevel partialUpdatedApplicantLevel = new ApplicantLevel();
        partialUpdatedApplicantLevel.setId(applicantLevel.getId());

        partialUpdatedApplicantLevel
            .code(UPDATED_CODE)
            .levelName(UPDATED_LEVEL_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restApplicantLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantLevel))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
        ApplicantLevel testApplicantLevel = applicantLevelList.get(applicantLevelList.size() - 1);
        assertThat(testApplicantLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicantLevel.getLevelName()).isEqualTo(UPDATED_LEVEL_NAME);
        assertThat(testApplicantLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicantLevel.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testApplicantLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApplicantLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicantLevel.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantLevelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicantLevel() throws Exception {
        int databaseSizeBeforeUpdate = applicantLevelRepository.findAll().size();
        applicantLevel.setId(count.incrementAndGet());

        // Create the ApplicantLevel
        ApplicantLevelDTO applicantLevelDTO = applicantLevelMapper.toDto(applicantLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantLevel in the database
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicantLevel() throws Exception {
        // Initialize the database
        applicantLevelRepository.saveAndFlush(applicantLevel);

        int databaseSizeBeforeDelete = applicantLevelRepository.findAll().size();

        // Delete the applicantLevel
        restApplicantLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicantLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantLevel> applicantLevelList = applicantLevelRepository.findAll();
        assertThat(applicantLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
