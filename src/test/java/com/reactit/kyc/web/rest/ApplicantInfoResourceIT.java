package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.domain.enumeration.Gender;
import com.reactit.kyc.repository.ApplicantInfoRepository;
import com.reactit.kyc.service.criteria.ApplicantInfoCriteria;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import com.reactit.kyc.service.mapper.ApplicantInfoMapper;
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
 * Integration tests for the {@link ApplicantInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantInfoResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSES = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSES = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_STATE_OF_BIRTH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String ENTITY_API_URL = "/api/applicant-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantInfoRepository applicantInfoRepository;

    @Autowired
    private ApplicantInfoMapper applicantInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantInfoMockMvc;

    private ApplicantInfo applicantInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantInfo createEntity(EntityManager em) {
        ApplicantInfo applicantInfo = new ApplicantInfo()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .addresses(DEFAULT_ADDRESSES)
            .email(DEFAULT_EMAIL)
            .middleName(DEFAULT_MIDDLE_NAME)
            .stateOfBirth(DEFAULT_STATE_OF_BIRTH)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .nationality(DEFAULT_NATIONALITY)
            .gender(DEFAULT_GENDER);
        return applicantInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantInfo createUpdatedEntity(EntityManager em) {
        ApplicantInfo applicantInfo = new ApplicantInfo()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .addresses(UPDATED_ADDRESSES)
            .email(UPDATED_EMAIL)
            .middleName(UPDATED_MIDDLE_NAME)
            .stateOfBirth(UPDATED_STATE_OF_BIRTH)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER);
        return applicantInfo;
    }

    @BeforeEach
    public void initTest() {
        applicantInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicantInfo() throws Exception {
        int databaseSizeBeforeCreate = applicantInfoRepository.findAll().size();
        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);
        restApplicantInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantInfo testApplicantInfo = applicantInfoList.get(applicantInfoList.size() - 1);
        assertThat(testApplicantInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicantInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicantInfo.getAddresses()).isEqualTo(DEFAULT_ADDRESSES);
        assertThat(testApplicantInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApplicantInfo.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplicantInfo.getStateOfBirth()).isEqualTo(DEFAULT_STATE_OF_BIRTH);
        assertThat(testApplicantInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testApplicantInfo.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testApplicantInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testApplicantInfo.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void createApplicantInfoWithExistingId() throws Exception {
        // Create the ApplicantInfo with an existing ID
        applicantInfo.setId(1L);
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        int databaseSizeBeforeCreate = applicantInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicantInfos() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].addresses").value(hasItem(DEFAULT_ADDRESSES)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].stateOfBirth").value(hasItem(DEFAULT_STATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    void getApplicantInfo() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get the applicantInfo
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, applicantInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantInfo.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.addresses").value(DEFAULT_ADDRESSES))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.stateOfBirth").value(DEFAULT_STATE_OF_BIRTH))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    void getApplicantInfosByIdFiltering() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        Long id = applicantInfo.getId();

        defaultApplicantInfoShouldBeFound("id.equals=" + id);
        defaultApplicantInfoShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName equals to DEFAULT_FIRST_NAME
        defaultApplicantInfoShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantInfoShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName not equals to DEFAULT_FIRST_NAME
        defaultApplicantInfoShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantInfoList where firstName not equals to UPDATED_FIRST_NAME
        defaultApplicantInfoShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultApplicantInfoShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the applicantInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantInfoShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName is not null
        defaultApplicantInfoShouldBeFound("firstName.specified=true");

        // Get all the applicantInfoList where firstName is null
        defaultApplicantInfoShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName contains DEFAULT_FIRST_NAME
        defaultApplicantInfoShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the applicantInfoList where firstName contains UPDATED_FIRST_NAME
        defaultApplicantInfoShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where firstName does not contain DEFAULT_FIRST_NAME
        defaultApplicantInfoShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the applicantInfoList where firstName does not contain UPDATED_FIRST_NAME
        defaultApplicantInfoShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName equals to DEFAULT_LAST_NAME
        defaultApplicantInfoShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the applicantInfoList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantInfoShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName not equals to DEFAULT_LAST_NAME
        defaultApplicantInfoShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the applicantInfoList where lastName not equals to UPDATED_LAST_NAME
        defaultApplicantInfoShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultApplicantInfoShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the applicantInfoList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantInfoShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName is not null
        defaultApplicantInfoShouldBeFound("lastName.specified=true");

        // Get all the applicantInfoList where lastName is null
        defaultApplicantInfoShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName contains DEFAULT_LAST_NAME
        defaultApplicantInfoShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the applicantInfoList where lastName contains UPDATED_LAST_NAME
        defaultApplicantInfoShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where lastName does not contain DEFAULT_LAST_NAME
        defaultApplicantInfoShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the applicantInfoList where lastName does not contain UPDATED_LAST_NAME
        defaultApplicantInfoShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses equals to DEFAULT_ADDRESSES
        defaultApplicantInfoShouldBeFound("addresses.equals=" + DEFAULT_ADDRESSES);

        // Get all the applicantInfoList where addresses equals to UPDATED_ADDRESSES
        defaultApplicantInfoShouldNotBeFound("addresses.equals=" + UPDATED_ADDRESSES);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses not equals to DEFAULT_ADDRESSES
        defaultApplicantInfoShouldNotBeFound("addresses.notEquals=" + DEFAULT_ADDRESSES);

        // Get all the applicantInfoList where addresses not equals to UPDATED_ADDRESSES
        defaultApplicantInfoShouldBeFound("addresses.notEquals=" + UPDATED_ADDRESSES);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses in DEFAULT_ADDRESSES or UPDATED_ADDRESSES
        defaultApplicantInfoShouldBeFound("addresses.in=" + DEFAULT_ADDRESSES + "," + UPDATED_ADDRESSES);

        // Get all the applicantInfoList where addresses equals to UPDATED_ADDRESSES
        defaultApplicantInfoShouldNotBeFound("addresses.in=" + UPDATED_ADDRESSES);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses is not null
        defaultApplicantInfoShouldBeFound("addresses.specified=true");

        // Get all the applicantInfoList where addresses is null
        defaultApplicantInfoShouldNotBeFound("addresses.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses contains DEFAULT_ADDRESSES
        defaultApplicantInfoShouldBeFound("addresses.contains=" + DEFAULT_ADDRESSES);

        // Get all the applicantInfoList where addresses contains UPDATED_ADDRESSES
        defaultApplicantInfoShouldNotBeFound("addresses.contains=" + UPDATED_ADDRESSES);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByAddressesNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where addresses does not contain DEFAULT_ADDRESSES
        defaultApplicantInfoShouldNotBeFound("addresses.doesNotContain=" + DEFAULT_ADDRESSES);

        // Get all the applicantInfoList where addresses does not contain UPDATED_ADDRESSES
        defaultApplicantInfoShouldBeFound("addresses.doesNotContain=" + UPDATED_ADDRESSES);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email equals to DEFAULT_EMAIL
        defaultApplicantInfoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the applicantInfoList where email equals to UPDATED_EMAIL
        defaultApplicantInfoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email not equals to DEFAULT_EMAIL
        defaultApplicantInfoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the applicantInfoList where email not equals to UPDATED_EMAIL
        defaultApplicantInfoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultApplicantInfoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the applicantInfoList where email equals to UPDATED_EMAIL
        defaultApplicantInfoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email is not null
        defaultApplicantInfoShouldBeFound("email.specified=true");

        // Get all the applicantInfoList where email is null
        defaultApplicantInfoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email contains DEFAULT_EMAIL
        defaultApplicantInfoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the applicantInfoList where email contains UPDATED_EMAIL
        defaultApplicantInfoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where email does not contain DEFAULT_EMAIL
        defaultApplicantInfoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the applicantInfoList where email does not contain UPDATED_EMAIL
        defaultApplicantInfoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultApplicantInfoShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultApplicantInfoShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantInfoList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the applicantInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName is not null
        defaultApplicantInfoShouldBeFound("middleName.specified=true");

        // Get all the applicantInfoList where middleName is null
        defaultApplicantInfoShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName contains DEFAULT_MIDDLE_NAME
        defaultApplicantInfoShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantInfoList where middleName contains UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultApplicantInfoShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantInfoList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultApplicantInfoShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth equals to DEFAULT_STATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("stateOfBirth.equals=" + DEFAULT_STATE_OF_BIRTH);

        // Get all the applicantInfoList where stateOfBirth equals to UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.equals=" + UPDATED_STATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth not equals to DEFAULT_STATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.notEquals=" + DEFAULT_STATE_OF_BIRTH);

        // Get all the applicantInfoList where stateOfBirth not equals to UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("stateOfBirth.notEquals=" + UPDATED_STATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth in DEFAULT_STATE_OF_BIRTH or UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("stateOfBirth.in=" + DEFAULT_STATE_OF_BIRTH + "," + UPDATED_STATE_OF_BIRTH);

        // Get all the applicantInfoList where stateOfBirth equals to UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.in=" + UPDATED_STATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth is not null
        defaultApplicantInfoShouldBeFound("stateOfBirth.specified=true");

        // Get all the applicantInfoList where stateOfBirth is null
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth contains DEFAULT_STATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("stateOfBirth.contains=" + DEFAULT_STATE_OF_BIRTH);

        // Get all the applicantInfoList where stateOfBirth contains UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.contains=" + UPDATED_STATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByStateOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where stateOfBirth does not contain DEFAULT_STATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("stateOfBirth.doesNotContain=" + DEFAULT_STATE_OF_BIRTH);

        // Get all the applicantInfoList where stateOfBirth does not contain UPDATED_STATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("stateOfBirth.doesNotContain=" + UPDATED_STATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth is not null
        defaultApplicantInfoShouldBeFound("dateOfBirth.specified=true");

        // Get all the applicantInfoList where dateOfBirth is null
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantInfoList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth equals to DEFAULT_PLACE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("placeOfBirth.equals=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantInfoList where placeOfBirth equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.equals=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth not equals to DEFAULT_PLACE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.notEquals=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantInfoList where placeOfBirth not equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("placeOfBirth.notEquals=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth in DEFAULT_PLACE_OF_BIRTH or UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("placeOfBirth.in=" + DEFAULT_PLACE_OF_BIRTH + "," + UPDATED_PLACE_OF_BIRTH);

        // Get all the applicantInfoList where placeOfBirth equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.in=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth is not null
        defaultApplicantInfoShouldBeFound("placeOfBirth.specified=true");

        // Get all the applicantInfoList where placeOfBirth is null
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth contains DEFAULT_PLACE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("placeOfBirth.contains=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantInfoList where placeOfBirth contains UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.contains=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByPlaceOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where placeOfBirth does not contain DEFAULT_PLACE_OF_BIRTH
        defaultApplicantInfoShouldNotBeFound("placeOfBirth.doesNotContain=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantInfoList where placeOfBirth does not contain UPDATED_PLACE_OF_BIRTH
        defaultApplicantInfoShouldBeFound("placeOfBirth.doesNotContain=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality equals to DEFAULT_NATIONALITY
        defaultApplicantInfoShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the applicantInfoList where nationality equals to UPDATED_NATIONALITY
        defaultApplicantInfoShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality not equals to DEFAULT_NATIONALITY
        defaultApplicantInfoShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the applicantInfoList where nationality not equals to UPDATED_NATIONALITY
        defaultApplicantInfoShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultApplicantInfoShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the applicantInfoList where nationality equals to UPDATED_NATIONALITY
        defaultApplicantInfoShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality is not null
        defaultApplicantInfoShouldBeFound("nationality.specified=true");

        // Get all the applicantInfoList where nationality is null
        defaultApplicantInfoShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality contains DEFAULT_NATIONALITY
        defaultApplicantInfoShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the applicantInfoList where nationality contains UPDATED_NATIONALITY
        defaultApplicantInfoShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where nationality does not contain DEFAULT_NATIONALITY
        defaultApplicantInfoShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the applicantInfoList where nationality does not contain UPDATED_NATIONALITY
        defaultApplicantInfoShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where gender equals to DEFAULT_GENDER
        defaultApplicantInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the applicantInfoList where gender equals to UPDATED_GENDER
        defaultApplicantInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where gender not equals to DEFAULT_GENDER
        defaultApplicantInfoShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the applicantInfoList where gender not equals to UPDATED_GENDER
        defaultApplicantInfoShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultApplicantInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the applicantInfoList where gender equals to UPDATED_GENDER
        defaultApplicantInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllApplicantInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        // Get all the applicantInfoList where gender is not null
        defaultApplicantInfoShouldBeFound("gender.specified=true");

        // Get all the applicantInfoList where gender is null
        defaultApplicantInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantInfosByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);
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
        applicantInfo.setApplicant(applicant);
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Long applicantId = applicant.getId();

        // Get all the applicantInfoList where applicant equals to applicantId
        defaultApplicantInfoShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the applicantInfoList where applicant equals to (applicantId + 1)
        defaultApplicantInfoShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantInfosByApplicantAddresseIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);
        ApplicantAddresse applicantAddresse;
        if (TestUtil.findAll(em, ApplicantAddresse.class).isEmpty()) {
            applicantAddresse = ApplicantAddresseResourceIT.createEntity(em);
            em.persist(applicantAddresse);
            em.flush();
        } else {
            applicantAddresse = TestUtil.findAll(em, ApplicantAddresse.class).get(0);
        }
        em.persist(applicantAddresse);
        em.flush();
        applicantInfo.addApplicantAddresse(applicantAddresse);
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Long applicantAddresseId = applicantAddresse.getId();

        // Get all the applicantInfoList where applicantAddresse equals to applicantAddresseId
        defaultApplicantInfoShouldBeFound("applicantAddresseId.equals=" + applicantAddresseId);

        // Get all the applicantInfoList where applicantAddresse equals to (applicantAddresseId + 1)
        defaultApplicantInfoShouldNotBeFound("applicantAddresseId.equals=" + (applicantAddresseId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantInfosByApplicantPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);
        ApplicantPhone applicantPhone;
        if (TestUtil.findAll(em, ApplicantPhone.class).isEmpty()) {
            applicantPhone = ApplicantPhoneResourceIT.createEntity(em);
            em.persist(applicantPhone);
            em.flush();
        } else {
            applicantPhone = TestUtil.findAll(em, ApplicantPhone.class).get(0);
        }
        em.persist(applicantPhone);
        em.flush();
        applicantInfo.addApplicantPhone(applicantPhone);
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Long applicantPhoneId = applicantPhone.getId();

        // Get all the applicantInfoList where applicantPhone equals to applicantPhoneId
        defaultApplicantInfoShouldBeFound("applicantPhoneId.equals=" + applicantPhoneId);

        // Get all the applicantInfoList where applicantPhone equals to (applicantPhoneId + 1)
        defaultApplicantInfoShouldNotBeFound("applicantPhoneId.equals=" + (applicantPhoneId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantInfosByApplicantDocsIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);
        ApplicantDocs applicantDocs;
        if (TestUtil.findAll(em, ApplicantDocs.class).isEmpty()) {
            applicantDocs = ApplicantDocsResourceIT.createEntity(em);
            em.persist(applicantDocs);
            em.flush();
        } else {
            applicantDocs = TestUtil.findAll(em, ApplicantDocs.class).get(0);
        }
        em.persist(applicantDocs);
        em.flush();
        applicantInfo.addApplicantDocs(applicantDocs);
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Long applicantDocsId = applicantDocs.getId();

        // Get all the applicantInfoList where applicantDocs equals to applicantDocsId
        defaultApplicantInfoShouldBeFound("applicantDocsId.equals=" + applicantDocsId);

        // Get all the applicantInfoList where applicantDocs equals to (applicantDocsId + 1)
        defaultApplicantInfoShouldNotBeFound("applicantDocsId.equals=" + (applicantDocsId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantInfosByCountryOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Country countryOfBirth;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            countryOfBirth = CountryResourceIT.createEntity(em);
            em.persist(countryOfBirth);
            em.flush();
        } else {
            countryOfBirth = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(countryOfBirth);
        em.flush();
        applicantInfo.addCountryOfBirth(countryOfBirth);
        applicantInfoRepository.saveAndFlush(applicantInfo);
        Long countryOfBirthId = countryOfBirth.getId();

        // Get all the applicantInfoList where countryOfBirth equals to countryOfBirthId
        defaultApplicantInfoShouldBeFound("countryOfBirthId.equals=" + countryOfBirthId);

        // Get all the applicantInfoList where countryOfBirth equals to (countryOfBirthId + 1)
        defaultApplicantInfoShouldNotBeFound("countryOfBirthId.equals=" + (countryOfBirthId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantInfoShouldBeFound(String filter) throws Exception {
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].addresses").value(hasItem(DEFAULT_ADDRESSES)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].stateOfBirth").value(hasItem(DEFAULT_STATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));

        // Check, that the count call also returns 1
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantInfoShouldNotBeFound(String filter) throws Exception {
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicantInfo() throws Exception {
        // Get the applicantInfo
        restApplicantInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicantInfo() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();

        // Update the applicantInfo
        ApplicantInfo updatedApplicantInfo = applicantInfoRepository.findById(applicantInfo.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantInfo are not directly saved in db
        em.detach(updatedApplicantInfo);
        updatedApplicantInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .addresses(UPDATED_ADDRESSES)
            .email(UPDATED_EMAIL)
            .middleName(UPDATED_MIDDLE_NAME)
            .stateOfBirth(UPDATED_STATE_OF_BIRTH)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER);
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(updatedApplicantInfo);

        restApplicantInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicantInfo testApplicantInfo = applicantInfoList.get(applicantInfoList.size() - 1);
        assertThat(testApplicantInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicantInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantInfo.getAddresses()).isEqualTo(UPDATED_ADDRESSES);
        assertThat(testApplicantInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicantInfo.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicantInfo.getStateOfBirth()).isEqualTo(UPDATED_STATE_OF_BIRTH);
        assertThat(testApplicantInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantInfo.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testApplicantInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplicantInfo.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void putNonExistingApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantInfoWithPatch() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();

        // Update the applicantInfo using partial update
        ApplicantInfo partialUpdatedApplicantInfo = new ApplicantInfo();
        partialUpdatedApplicantInfo.setId(applicantInfo.getId());

        partialUpdatedApplicantInfo
            .addresses(UPDATED_ADDRESSES)
            .email(UPDATED_EMAIL)
            .middleName(UPDATED_MIDDLE_NAME)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER);

        restApplicantInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantInfo))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicantInfo testApplicantInfo = applicantInfoList.get(applicantInfoList.size() - 1);
        assertThat(testApplicantInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicantInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicantInfo.getAddresses()).isEqualTo(UPDATED_ADDRESSES);
        assertThat(testApplicantInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicantInfo.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicantInfo.getStateOfBirth()).isEqualTo(DEFAULT_STATE_OF_BIRTH);
        assertThat(testApplicantInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testApplicantInfo.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testApplicantInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplicantInfo.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void fullUpdateApplicantInfoWithPatch() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();

        // Update the applicantInfo using partial update
        ApplicantInfo partialUpdatedApplicantInfo = new ApplicantInfo();
        partialUpdatedApplicantInfo.setId(applicantInfo.getId());

        partialUpdatedApplicantInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .addresses(UPDATED_ADDRESSES)
            .email(UPDATED_EMAIL)
            .middleName(UPDATED_MIDDLE_NAME)
            .stateOfBirth(UPDATED_STATE_OF_BIRTH)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER);

        restApplicantInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantInfo))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicantInfo testApplicantInfo = applicantInfoList.get(applicantInfoList.size() - 1);
        assertThat(testApplicantInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicantInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantInfo.getAddresses()).isEqualTo(UPDATED_ADDRESSES);
        assertThat(testApplicantInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicantInfo.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicantInfo.getStateOfBirth()).isEqualTo(UPDATED_STATE_OF_BIRTH);
        assertThat(testApplicantInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantInfo.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testApplicantInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplicantInfo.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void patchNonExistingApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicantInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantInfoRepository.findAll().size();
        applicantInfo.setId(count.incrementAndGet());

        // Create the ApplicantInfo
        ApplicantInfoDTO applicantInfoDTO = applicantInfoMapper.toDto(applicantInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantInfo in the database
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicantInfo() throws Exception {
        // Initialize the database
        applicantInfoRepository.saveAndFlush(applicantInfo);

        int databaseSizeBeforeDelete = applicantInfoRepository.findAll().size();

        // Delete the applicantInfo
        restApplicantInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicantInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantInfo> applicantInfoList = applicantInfoRepository.findAll();
        assertThat(applicantInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
