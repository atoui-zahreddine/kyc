package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import com.reactit.kyc.repository.ApplicantDocsRepository;
import com.reactit.kyc.service.criteria.ApplicantDocsCriteria;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import com.reactit.kyc.service.mapper.ApplicantDocsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ApplicantDocsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantDocsResourceIT {

    private static final TypeDoc DEFAULT_DOC_TYPE = TypeDoc.ID_CARD;
    private static final TypeDoc UPDATED_DOC_TYPE = TypeDoc.PASSPORT;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_UNTIL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_UNTIL = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final SubType DEFAULT_SUB_TYPES = SubType.FRONT_SIDE;
    private static final SubType UPDATED_SUB_TYPES = SubType.BACK_SIDE;

    private static final String DEFAULT_IMAGE_TRUST = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_TRUST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applicant-docs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantDocsRepository applicantDocsRepository;

    @Autowired
    private ApplicantDocsMapper applicantDocsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantDocsMockMvc;

    private ApplicantDocs applicantDocs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantDocs createEntity(EntityManager em) {
        ApplicantDocs applicantDocs = new ApplicantDocs()
            .docType(DEFAULT_DOC_TYPE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .number(DEFAULT_NUMBER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .validUntil(DEFAULT_VALID_UNTIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .subTypes(DEFAULT_SUB_TYPES)
            .imageTrust(DEFAULT_IMAGE_TRUST);
        return applicantDocs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantDocs createUpdatedEntity(EntityManager em) {
        ApplicantDocs applicantDocs = new ApplicantDocs()
            .docType(UPDATED_DOC_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .number(UPDATED_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .validUntil(UPDATED_VALID_UNTIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .subTypes(UPDATED_SUB_TYPES)
            .imageTrust(UPDATED_IMAGE_TRUST);
        return applicantDocs;
    }

    @BeforeEach
    public void initTest() {
        applicantDocs = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicantDocs() throws Exception {
        int databaseSizeBeforeCreate = applicantDocsRepository.findAll().size();
        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);
        restApplicantDocsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantDocs testApplicantDocs = applicantDocsList.get(applicantDocsList.size() - 1);
        assertThat(testApplicantDocs.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testApplicantDocs.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicantDocs.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicantDocs.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testApplicantDocs.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testApplicantDocs.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testApplicantDocs.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testApplicantDocs.getSubTypes()).isEqualTo(DEFAULT_SUB_TYPES);
        assertThat(testApplicantDocs.getImageTrust()).isEqualTo(DEFAULT_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void createApplicantDocsWithExistingId() throws Exception {
        // Create the ApplicantDocs with an existing ID
        applicantDocs.setId(1L);
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        int databaseSizeBeforeCreate = applicantDocsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantDocsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicantDocs() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].subTypes").value(hasItem(DEFAULT_SUB_TYPES.toString())))
            .andExpect(jsonPath("$.[*].imageTrust").value(hasItem(DEFAULT_IMAGE_TRUST)));
    }

    @Test
    @Transactional
    void getApplicantDocs() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get the applicantDocs
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL_ID, applicantDocs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantDocs.getId().intValue()))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.subTypes").value(DEFAULT_SUB_TYPES.toString()))
            .andExpect(jsonPath("$.imageTrust").value(DEFAULT_IMAGE_TRUST));
    }

    @Test
    @Transactional
    void getApplicantDocsByIdFiltering() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        Long id = applicantDocs.getId();

        defaultApplicantDocsShouldBeFound("id.equals=" + id);
        defaultApplicantDocsShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantDocsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantDocsShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantDocsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantDocsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where docType equals to DEFAULT_DOC_TYPE
        defaultApplicantDocsShouldBeFound("docType.equals=" + DEFAULT_DOC_TYPE);

        // Get all the applicantDocsList where docType equals to UPDATED_DOC_TYPE
        defaultApplicantDocsShouldNotBeFound("docType.equals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDocTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where docType not equals to DEFAULT_DOC_TYPE
        defaultApplicantDocsShouldNotBeFound("docType.notEquals=" + DEFAULT_DOC_TYPE);

        // Get all the applicantDocsList where docType not equals to UPDATED_DOC_TYPE
        defaultApplicantDocsShouldBeFound("docType.notEquals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where docType in DEFAULT_DOC_TYPE or UPDATED_DOC_TYPE
        defaultApplicantDocsShouldBeFound("docType.in=" + DEFAULT_DOC_TYPE + "," + UPDATED_DOC_TYPE);

        // Get all the applicantDocsList where docType equals to UPDATED_DOC_TYPE
        defaultApplicantDocsShouldNotBeFound("docType.in=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where docType is not null
        defaultApplicantDocsShouldBeFound("docType.specified=true");

        // Get all the applicantDocsList where docType is null
        defaultApplicantDocsShouldNotBeFound("docType.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName equals to DEFAULT_FIRST_NAME
        defaultApplicantDocsShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantDocsList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantDocsShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName not equals to DEFAULT_FIRST_NAME
        defaultApplicantDocsShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantDocsList where firstName not equals to UPDATED_FIRST_NAME
        defaultApplicantDocsShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultApplicantDocsShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the applicantDocsList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantDocsShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName is not null
        defaultApplicantDocsShouldBeFound("firstName.specified=true");

        // Get all the applicantDocsList where firstName is null
        defaultApplicantDocsShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName contains DEFAULT_FIRST_NAME
        defaultApplicantDocsShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the applicantDocsList where firstName contains UPDATED_FIRST_NAME
        defaultApplicantDocsShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where firstName does not contain DEFAULT_FIRST_NAME
        defaultApplicantDocsShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the applicantDocsList where firstName does not contain UPDATED_FIRST_NAME
        defaultApplicantDocsShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName equals to DEFAULT_LAST_NAME
        defaultApplicantDocsShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the applicantDocsList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantDocsShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName not equals to DEFAULT_LAST_NAME
        defaultApplicantDocsShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the applicantDocsList where lastName not equals to UPDATED_LAST_NAME
        defaultApplicantDocsShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultApplicantDocsShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the applicantDocsList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantDocsShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName is not null
        defaultApplicantDocsShouldBeFound("lastName.specified=true");

        // Get all the applicantDocsList where lastName is null
        defaultApplicantDocsShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName contains DEFAULT_LAST_NAME
        defaultApplicantDocsShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the applicantDocsList where lastName contains UPDATED_LAST_NAME
        defaultApplicantDocsShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where lastName does not contain DEFAULT_LAST_NAME
        defaultApplicantDocsShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the applicantDocsList where lastName does not contain UPDATED_LAST_NAME
        defaultApplicantDocsShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number equals to DEFAULT_NUMBER
        defaultApplicantDocsShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the applicantDocsList where number equals to UPDATED_NUMBER
        defaultApplicantDocsShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number not equals to DEFAULT_NUMBER
        defaultApplicantDocsShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the applicantDocsList where number not equals to UPDATED_NUMBER
        defaultApplicantDocsShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultApplicantDocsShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the applicantDocsList where number equals to UPDATED_NUMBER
        defaultApplicantDocsShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number is not null
        defaultApplicantDocsShouldBeFound("number.specified=true");

        // Get all the applicantDocsList where number is null
        defaultApplicantDocsShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number contains DEFAULT_NUMBER
        defaultApplicantDocsShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the applicantDocsList where number contains UPDATED_NUMBER
        defaultApplicantDocsShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where number does not contain DEFAULT_NUMBER
        defaultApplicantDocsShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the applicantDocsList where number does not contain UPDATED_NUMBER
        defaultApplicantDocsShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth is not null
        defaultApplicantDocsShouldBeFound("dateOfBirth.specified=true");

        // Get all the applicantDocsList where dateOfBirth is null
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultApplicantDocsShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantDocsList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultApplicantDocsShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil equals to DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.equals=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil equals to UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.equals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil not equals to DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.notEquals=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil not equals to UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.notEquals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil in DEFAULT_VALID_UNTIL or UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.in=" + DEFAULT_VALID_UNTIL + "," + UPDATED_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil equals to UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.in=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil is not null
        defaultApplicantDocsShouldBeFound("validUntil.specified=true");

        // Get all the applicantDocsList where validUntil is null
        defaultApplicantDocsShouldNotBeFound("validUntil.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil is greater than or equal to DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.greaterThanOrEqual=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil is greater than or equal to UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.greaterThanOrEqual=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil is less than or equal to DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.lessThanOrEqual=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil is less than or equal to SMALLER_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.lessThanOrEqual=" + SMALLER_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil is less than DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.lessThan=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil is less than UPDATED_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.lessThan=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByValidUntilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where validUntil is greater than DEFAULT_VALID_UNTIL
        defaultApplicantDocsShouldNotBeFound("validUntil.greaterThan=" + DEFAULT_VALID_UNTIL);

        // Get all the applicantDocsList where validUntil is greater than SMALLER_VALID_UNTIL
        defaultApplicantDocsShouldBeFound("validUntil.greaterThan=" + SMALLER_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultApplicantDocsShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the applicantDocsList where imageUrl equals to UPDATED_IMAGE_URL
        defaultApplicantDocsShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultApplicantDocsShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the applicantDocsList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultApplicantDocsShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultApplicantDocsShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the applicantDocsList where imageUrl equals to UPDATED_IMAGE_URL
        defaultApplicantDocsShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl is not null
        defaultApplicantDocsShouldBeFound("imageUrl.specified=true");

        // Get all the applicantDocsList where imageUrl is null
        defaultApplicantDocsShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl contains DEFAULT_IMAGE_URL
        defaultApplicantDocsShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the applicantDocsList where imageUrl contains UPDATED_IMAGE_URL
        defaultApplicantDocsShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultApplicantDocsShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the applicantDocsList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultApplicantDocsShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllApplicantDocsBySubTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where subTypes equals to DEFAULT_SUB_TYPES
        defaultApplicantDocsShouldBeFound("subTypes.equals=" + DEFAULT_SUB_TYPES);

        // Get all the applicantDocsList where subTypes equals to UPDATED_SUB_TYPES
        defaultApplicantDocsShouldNotBeFound("subTypes.equals=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllApplicantDocsBySubTypesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where subTypes not equals to DEFAULT_SUB_TYPES
        defaultApplicantDocsShouldNotBeFound("subTypes.notEquals=" + DEFAULT_SUB_TYPES);

        // Get all the applicantDocsList where subTypes not equals to UPDATED_SUB_TYPES
        defaultApplicantDocsShouldBeFound("subTypes.notEquals=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllApplicantDocsBySubTypesIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where subTypes in DEFAULT_SUB_TYPES or UPDATED_SUB_TYPES
        defaultApplicantDocsShouldBeFound("subTypes.in=" + DEFAULT_SUB_TYPES + "," + UPDATED_SUB_TYPES);

        // Get all the applicantDocsList where subTypes equals to UPDATED_SUB_TYPES
        defaultApplicantDocsShouldNotBeFound("subTypes.in=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllApplicantDocsBySubTypesIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where subTypes is not null
        defaultApplicantDocsShouldBeFound("subTypes.specified=true");

        // Get all the applicantDocsList where subTypes is null
        defaultApplicantDocsShouldNotBeFound("subTypes.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust equals to DEFAULT_IMAGE_TRUST
        defaultApplicantDocsShouldBeFound("imageTrust.equals=" + DEFAULT_IMAGE_TRUST);

        // Get all the applicantDocsList where imageTrust equals to UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldNotBeFound("imageTrust.equals=" + UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust not equals to DEFAULT_IMAGE_TRUST
        defaultApplicantDocsShouldNotBeFound("imageTrust.notEquals=" + DEFAULT_IMAGE_TRUST);

        // Get all the applicantDocsList where imageTrust not equals to UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldBeFound("imageTrust.notEquals=" + UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustIsInShouldWork() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust in DEFAULT_IMAGE_TRUST or UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldBeFound("imageTrust.in=" + DEFAULT_IMAGE_TRUST + "," + UPDATED_IMAGE_TRUST);

        // Get all the applicantDocsList where imageTrust equals to UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldNotBeFound("imageTrust.in=" + UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust is not null
        defaultApplicantDocsShouldBeFound("imageTrust.specified=true");

        // Get all the applicantDocsList where imageTrust is null
        defaultApplicantDocsShouldNotBeFound("imageTrust.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust contains DEFAULT_IMAGE_TRUST
        defaultApplicantDocsShouldBeFound("imageTrust.contains=" + DEFAULT_IMAGE_TRUST);

        // Get all the applicantDocsList where imageTrust contains UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldNotBeFound("imageTrust.contains=" + UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByImageTrustNotContainsSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        // Get all the applicantDocsList where imageTrust does not contain DEFAULT_IMAGE_TRUST
        defaultApplicantDocsShouldNotBeFound("imageTrust.doesNotContain=" + DEFAULT_IMAGE_TRUST);

        // Get all the applicantDocsList where imageTrust does not contain UPDATED_IMAGE_TRUST
        defaultApplicantDocsShouldBeFound("imageTrust.doesNotContain=" + UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void getAllApplicantDocsByDocsCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);
        Country docsCountry;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            docsCountry = CountryResourceIT.createEntity(em);
            em.persist(docsCountry);
            em.flush();
        } else {
            docsCountry = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(docsCountry);
        em.flush();
        applicantDocs.setDocsCountry(docsCountry);
        applicantDocsRepository.saveAndFlush(applicantDocs);
        Long docsCountryId = docsCountry.getId();

        // Get all the applicantDocsList where docsCountry equals to docsCountryId
        defaultApplicantDocsShouldBeFound("docsCountryId.equals=" + docsCountryId);

        // Get all the applicantDocsList where docsCountry equals to (docsCountryId + 1)
        defaultApplicantDocsShouldNotBeFound("docsCountryId.equals=" + (docsCountryId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantDocsByApplicantInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);
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
        applicantDocs.addApplicantInfo(applicantInfo);
        applicantDocsRepository.saveAndFlush(applicantDocs);
        Long applicantInfoId = applicantInfo.getId();

        // Get all the applicantDocsList where applicantInfo equals to applicantInfoId
        defaultApplicantDocsShouldBeFound("applicantInfoId.equals=" + applicantInfoId);

        // Get all the applicantDocsList where applicantInfo equals to (applicantInfoId + 1)
        defaultApplicantDocsShouldNotBeFound("applicantInfoId.equals=" + (applicantInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantDocsShouldBeFound(String filter) throws Exception {
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].subTypes").value(hasItem(DEFAULT_SUB_TYPES.toString())))
            .andExpect(jsonPath("$.[*].imageTrust").value(hasItem(DEFAULT_IMAGE_TRUST)));

        // Check, that the count call also returns 1
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantDocsShouldNotBeFound(String filter) throws Exception {
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantDocsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicantDocs() throws Exception {
        // Get the applicantDocs
        restApplicantDocsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicantDocs() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();

        // Update the applicantDocs
        ApplicantDocs updatedApplicantDocs = applicantDocsRepository.findById(applicantDocs.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantDocs are not directly saved in db
        em.detach(updatedApplicantDocs);
        updatedApplicantDocs
            .docType(UPDATED_DOC_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .number(UPDATED_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .validUntil(UPDATED_VALID_UNTIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .subTypes(UPDATED_SUB_TYPES)
            .imageTrust(UPDATED_IMAGE_TRUST);
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(updatedApplicantDocs);

        restApplicantDocsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantDocsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
        ApplicantDocs testApplicantDocs = applicantDocsList.get(applicantDocsList.size() - 1);
        assertThat(testApplicantDocs.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testApplicantDocs.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicantDocs.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantDocs.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testApplicantDocs.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantDocs.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testApplicantDocs.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testApplicantDocs.getSubTypes()).isEqualTo(UPDATED_SUB_TYPES);
        assertThat(testApplicantDocs.getImageTrust()).isEqualTo(UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void putNonExistingApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantDocsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantDocsWithPatch() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();

        // Update the applicantDocs using partial update
        ApplicantDocs partialUpdatedApplicantDocs = new ApplicantDocs();
        partialUpdatedApplicantDocs.setId(applicantDocs.getId());

        partialUpdatedApplicantDocs
            .docType(UPDATED_DOC_TYPE)
            .lastName(UPDATED_LAST_NAME)
            .number(UPDATED_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .validUntil(UPDATED_VALID_UNTIL)
            .subTypes(UPDATED_SUB_TYPES);

        restApplicantDocsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantDocs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantDocs))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
        ApplicantDocs testApplicantDocs = applicantDocsList.get(applicantDocsList.size() - 1);
        assertThat(testApplicantDocs.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testApplicantDocs.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicantDocs.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantDocs.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testApplicantDocs.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantDocs.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testApplicantDocs.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testApplicantDocs.getSubTypes()).isEqualTo(UPDATED_SUB_TYPES);
        assertThat(testApplicantDocs.getImageTrust()).isEqualTo(DEFAULT_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void fullUpdateApplicantDocsWithPatch() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();

        // Update the applicantDocs using partial update
        ApplicantDocs partialUpdatedApplicantDocs = new ApplicantDocs();
        partialUpdatedApplicantDocs.setId(applicantDocs.getId());

        partialUpdatedApplicantDocs
            .docType(UPDATED_DOC_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .number(UPDATED_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .validUntil(UPDATED_VALID_UNTIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .subTypes(UPDATED_SUB_TYPES)
            .imageTrust(UPDATED_IMAGE_TRUST);

        restApplicantDocsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantDocs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantDocs))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
        ApplicantDocs testApplicantDocs = applicantDocsList.get(applicantDocsList.size() - 1);
        assertThat(testApplicantDocs.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testApplicantDocs.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicantDocs.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantDocs.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testApplicantDocs.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantDocs.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testApplicantDocs.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testApplicantDocs.getSubTypes()).isEqualTo(UPDATED_SUB_TYPES);
        assertThat(testApplicantDocs.getImageTrust()).isEqualTo(UPDATED_IMAGE_TRUST);
    }

    @Test
    @Transactional
    void patchNonExistingApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantDocsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicantDocs() throws Exception {
        int databaseSizeBeforeUpdate = applicantDocsRepository.findAll().size();
        applicantDocs.setId(count.incrementAndGet());

        // Create the ApplicantDocs
        ApplicantDocsDTO applicantDocsDTO = applicantDocsMapper.toDto(applicantDocs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantDocsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantDocsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantDocs in the database
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicantDocs() throws Exception {
        // Initialize the database
        applicantDocsRepository.saveAndFlush(applicantDocs);

        int databaseSizeBeforeDelete = applicantDocsRepository.findAll().size();

        // Delete the applicantDocs
        restApplicantDocsMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicantDocs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantDocs> applicantDocsList = applicantDocsRepository.findAll();
        assertThat(applicantDocsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
