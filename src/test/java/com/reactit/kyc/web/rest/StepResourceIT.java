package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.domain.Step;
import com.reactit.kyc.repository.StepRepository;
import com.reactit.kyc.service.StepService;
import com.reactit.kyc.service.criteria.StepCriteria;
import com.reactit.kyc.service.dto.StepDTO;
import com.reactit.kyc.service.mapper.StepMapper;
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
 * Integration tests for the {@link StepResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StepResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;
    private static final Long SMALLER_CREATED_BY = 1L - 1L;

    private static final Instant DEFAULT_MODIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StepRepository stepRepository;

    @Mock
    private StepRepository stepRepositoryMock;

    @Autowired
    private StepMapper stepMapper;

    @Mock
    private StepService stepServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStepMockMvc;

    private Step step;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Step createEntity(EntityManager em) {
        Step step = new Step()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return step;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Step createUpdatedEntity(EntityManager em) {
        Step step = new Step()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return step;
    }

    @BeforeEach
    public void initTest() {
        step = createEntity(em);
    }

    @Test
    @Transactional
    void createStep() throws Exception {
        int databaseSizeBeforeCreate = stepRepository.findAll().size();
        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);
        restStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepDTO)))
            .andExpect(status().isCreated());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeCreate + 1);
        Step testStep = stepList.get(stepList.size() - 1);
        assertThat(testStep.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStep.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStep.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStep.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createStepWithExistingId() throws Exception {
        // Create the Step with an existing ID
        step.setId(1L);
        StepDTO stepDTO = stepMapper.toDto(step);

        int databaseSizeBeforeCreate = stepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSteps() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList
        restStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(step.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStepsWithEagerRelationshipsIsEnabled() throws Exception {
        when(stepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(stepServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStepsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(stepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(stepServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStep() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get the step
        restStepMockMvc
            .perform(get(ENTITY_API_URL_ID, step.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(step.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getStepsByIdFiltering() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        Long id = step.getId();

        defaultStepShouldBeFound("id.equals=" + id);
        defaultStepShouldNotBeFound("id.notEquals=" + id);

        defaultStepShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStepShouldNotBeFound("id.greaterThan=" + id);

        defaultStepShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStepShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStepsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code equals to DEFAULT_CODE
        defaultStepShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the stepList where code equals to UPDATED_CODE
        defaultStepShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStepsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code not equals to DEFAULT_CODE
        defaultStepShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the stepList where code not equals to UPDATED_CODE
        defaultStepShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStepsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code in DEFAULT_CODE or UPDATED_CODE
        defaultStepShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the stepList where code equals to UPDATED_CODE
        defaultStepShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStepsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code is not null
        defaultStepShouldBeFound("code.specified=true");

        // Get all the stepList where code is null
        defaultStepShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByCodeContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code contains DEFAULT_CODE
        defaultStepShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the stepList where code contains UPDATED_CODE
        defaultStepShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStepsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where code does not contain DEFAULT_CODE
        defaultStepShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the stepList where code does not contain UPDATED_CODE
        defaultStepShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStepsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name equals to DEFAULT_NAME
        defaultStepShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stepList where name equals to UPDATED_NAME
        defaultStepShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStepsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name not equals to DEFAULT_NAME
        defaultStepShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stepList where name not equals to UPDATED_NAME
        defaultStepShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStepsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStepShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stepList where name equals to UPDATED_NAME
        defaultStepShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStepsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name is not null
        defaultStepShouldBeFound("name.specified=true");

        // Get all the stepList where name is null
        defaultStepShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByNameContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name contains DEFAULT_NAME
        defaultStepShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stepList where name contains UPDATED_NAME
        defaultStepShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStepsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where name does not contain DEFAULT_NAME
        defaultStepShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stepList where name does not contain UPDATED_NAME
        defaultStepShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description equals to DEFAULT_DESCRIPTION
        defaultStepShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the stepList where description equals to UPDATED_DESCRIPTION
        defaultStepShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description not equals to DEFAULT_DESCRIPTION
        defaultStepShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the stepList where description not equals to UPDATED_DESCRIPTION
        defaultStepShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStepShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the stepList where description equals to UPDATED_DESCRIPTION
        defaultStepShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description is not null
        defaultStepShouldBeFound("description.specified=true");

        // Get all the stepList where description is null
        defaultStepShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description contains DEFAULT_DESCRIPTION
        defaultStepShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the stepList where description contains UPDATED_DESCRIPTION
        defaultStepShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStepsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where description does not contain DEFAULT_DESCRIPTION
        defaultStepShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the stepList where description does not contain UPDATED_DESCRIPTION
        defaultStepShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdAt equals to DEFAULT_CREATED_AT
        defaultStepShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the stepList where createdAt equals to UPDATED_CREATED_AT
        defaultStepShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdAt not equals to DEFAULT_CREATED_AT
        defaultStepShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the stepList where createdAt not equals to UPDATED_CREATED_AT
        defaultStepShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultStepShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the stepList where createdAt equals to UPDATED_CREATED_AT
        defaultStepShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdAt is not null
        defaultStepShouldBeFound("createdAt.specified=true");

        // Get all the stepList where createdAt is null
        defaultStepShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy equals to DEFAULT_CREATED_BY
        defaultStepShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy equals to UPDATED_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy not equals to DEFAULT_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy not equals to UPDATED_CREATED_BY
        defaultStepShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultStepShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the stepList where createdBy equals to UPDATED_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy is not null
        defaultStepShouldBeFound("createdBy.specified=true");

        // Get all the stepList where createdBy is null
        defaultStepShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultStepShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultStepShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy is less than DEFAULT_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy is less than UPDATED_CREATED_BY
        defaultStepShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where createdBy is greater than DEFAULT_CREATED_BY
        defaultStepShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the stepList where createdBy is greater than SMALLER_CREATED_BY
        defaultStepShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStepsByModifiedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where modifiedAt equals to DEFAULT_MODIFIED_AT
        defaultStepShouldBeFound("modifiedAt.equals=" + DEFAULT_MODIFIED_AT);

        // Get all the stepList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultStepShouldNotBeFound("modifiedAt.equals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByModifiedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where modifiedAt not equals to DEFAULT_MODIFIED_AT
        defaultStepShouldNotBeFound("modifiedAt.notEquals=" + DEFAULT_MODIFIED_AT);

        // Get all the stepList where modifiedAt not equals to UPDATED_MODIFIED_AT
        defaultStepShouldBeFound("modifiedAt.notEquals=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByModifiedAtIsInShouldWork() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where modifiedAt in DEFAULT_MODIFIED_AT or UPDATED_MODIFIED_AT
        defaultStepShouldBeFound("modifiedAt.in=" + DEFAULT_MODIFIED_AT + "," + UPDATED_MODIFIED_AT);

        // Get all the stepList where modifiedAt equals to UPDATED_MODIFIED_AT
        defaultStepShouldNotBeFound("modifiedAt.in=" + UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void getAllStepsByModifiedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        // Get all the stepList where modifiedAt is not null
        defaultStepShouldBeFound("modifiedAt.specified=true");

        // Get all the stepList where modifiedAt is null
        defaultStepShouldNotBeFound("modifiedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllStepsByDocSetIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);
        DocSet docSet;
        if (TestUtil.findAll(em, DocSet.class).isEmpty()) {
            docSet = DocSetResourceIT.createEntity(em);
            em.persist(docSet);
            em.flush();
        } else {
            docSet = TestUtil.findAll(em, DocSet.class).get(0);
        }
        em.persist(docSet);
        em.flush();
        step.addDocSet(docSet);
        stepRepository.saveAndFlush(step);
        Long docSetId = docSet.getId();

        // Get all the stepList where docSet equals to docSetId
        defaultStepShouldBeFound("docSetId.equals=" + docSetId);

        // Get all the stepList where docSet equals to (docSetId + 1)
        defaultStepShouldNotBeFound("docSetId.equals=" + (docSetId + 1));
    }

    @Test
    @Transactional
    void getAllStepsByApplicantLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);
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
        step.addApplicantLevel(applicantLevel);
        stepRepository.saveAndFlush(step);
        Long applicantLevelId = applicantLevel.getId();

        // Get all the stepList where applicantLevel equals to applicantLevelId
        defaultStepShouldBeFound("applicantLevelId.equals=" + applicantLevelId);

        // Get all the stepList where applicantLevel equals to (applicantLevelId + 1)
        defaultStepShouldNotBeFound("applicantLevelId.equals=" + (applicantLevelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStepShouldBeFound(String filter) throws Exception {
        restStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(step.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));

        // Check, that the count call also returns 1
        restStepMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStepShouldNotBeFound(String filter) throws Exception {
        restStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStepMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStep() throws Exception {
        // Get the step
        restStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStep() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        int databaseSizeBeforeUpdate = stepRepository.findAll().size();

        // Update the step
        Step updatedStep = stepRepository.findById(step.getId()).get();
        // Disconnect from session so that the updates on updatedStep are not directly saved in db
        em.detach(updatedStep);
        updatedStep
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        StepDTO stepDTO = stepMapper.toDto(updatedStep);

        restStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepDTO))
            )
            .andExpect(status().isOk());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
        Step testStep = stepList.get(stepList.size() - 1);
        assertThat(testStep.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStep.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStep.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStep.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStepWithPatch() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        int databaseSizeBeforeUpdate = stepRepository.findAll().size();

        // Update the step using partial update
        Step partialUpdatedStep = new Step();
        partialUpdatedStep.setId(step.getId());

        partialUpdatedStep
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStep))
            )
            .andExpect(status().isOk());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
        Step testStep = stepList.get(stepList.size() - 1);
        assertThat(testStep.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStep.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStep.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStep.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateStepWithPatch() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        int databaseSizeBeforeUpdate = stepRepository.findAll().size();

        // Update the step using partial update
        Step partialUpdatedStep = new Step();
        partialUpdatedStep.setId(step.getId());

        partialUpdatedStep
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStep))
            )
            .andExpect(status().isOk());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
        Step testStep = stepList.get(stepList.size() - 1);
        assertThat(testStep.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStep.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStep.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStep.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stepDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStep() throws Exception {
        int databaseSizeBeforeUpdate = stepRepository.findAll().size();
        step.setId(count.incrementAndGet());

        // Create the Step
        StepDTO stepDTO = stepMapper.toDto(step);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stepDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Step in the database
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStep() throws Exception {
        // Initialize the database
        stepRepository.saveAndFlush(step);

        int databaseSizeBeforeDelete = stepRepository.findAll().size();

        // Delete the step
        restStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, step.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Step> stepList = stepRepository.findAll();
        assertThat(stepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
