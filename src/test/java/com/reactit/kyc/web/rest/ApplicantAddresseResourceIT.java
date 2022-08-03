package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.repository.ApplicantAddresseRepository;
import com.reactit.kyc.service.criteria.ApplicantAddresseCriteria;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import com.reactit.kyc.service.mapper.ApplicantAddresseMapper;
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
 * Integration tests for the {@link ApplicantAddresseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantAddresseResourceIT {

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_STREET = "AAAAAAAAAA";
    private static final String UPDATED_SUB_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_TOWN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/applicant-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantAddresseRepository applicantAddresseRepository;

    @Autowired
    private ApplicantAddresseMapper applicantAddresseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantAddresseMockMvc;

    private ApplicantAddresse applicantAddresse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantAddresse createEntity(EntityManager em) {
        ApplicantAddresse applicantAddresse = new ApplicantAddresse()
            .postCode(DEFAULT_POST_CODE)
            .state(DEFAULT_STATE)
            .street(DEFAULT_STREET)
            .subStreet(DEFAULT_SUB_STREET)
            .town(DEFAULT_TOWN)
            .enabled(DEFAULT_ENABLED);
        return applicantAddresse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantAddresse createUpdatedEntity(EntityManager em) {
        ApplicantAddresse applicantAddresse = new ApplicantAddresse()
            .postCode(UPDATED_POST_CODE)
            .state(UPDATED_STATE)
            .street(UPDATED_STREET)
            .subStreet(UPDATED_SUB_STREET)
            .town(UPDATED_TOWN)
            .enabled(UPDATED_ENABLED);
        return applicantAddresse;
    }

    @BeforeEach
    public void initTest() {
        applicantAddresse = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicantAddresse() throws Exception {
        int databaseSizeBeforeCreate = applicantAddresseRepository.findAll().size();
        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);
        restApplicantAddresseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantAddresse testApplicantAddresse = applicantAddresseList.get(applicantAddresseList.size() - 1);
        assertThat(testApplicantAddresse.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testApplicantAddresse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testApplicantAddresse.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testApplicantAddresse.getSubStreet()).isEqualTo(DEFAULT_SUB_STREET);
        assertThat(testApplicantAddresse.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testApplicantAddresse.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    void createApplicantAddresseWithExistingId() throws Exception {
        // Create the ApplicantAddresse with an existing ID
        applicantAddresse.setId(1L);
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        int databaseSizeBeforeCreate = applicantAddresseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantAddresseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicantAddresses() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantAddresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].subStreet").value(hasItem(DEFAULT_SUB_STREET)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    void getApplicantAddresse() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get the applicantAddresse
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL_ID, applicantAddresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantAddresse.getId().intValue()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.subStreet").value(DEFAULT_SUB_STREET))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    void getApplicantAddressesByIdFiltering() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        Long id = applicantAddresse.getId();

        defaultApplicantAddresseShouldBeFound("id.equals=" + id);
        defaultApplicantAddresseShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantAddresseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantAddresseShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantAddresseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantAddresseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode equals to DEFAULT_POST_CODE
        defaultApplicantAddresseShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the applicantAddresseList where postCode equals to UPDATED_POST_CODE
        defaultApplicantAddresseShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode not equals to DEFAULT_POST_CODE
        defaultApplicantAddresseShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the applicantAddresseList where postCode not equals to UPDATED_POST_CODE
        defaultApplicantAddresseShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultApplicantAddresseShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the applicantAddresseList where postCode equals to UPDATED_POST_CODE
        defaultApplicantAddresseShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode is not null
        defaultApplicantAddresseShouldBeFound("postCode.specified=true");

        // Get all the applicantAddresseList where postCode is null
        defaultApplicantAddresseShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode contains DEFAULT_POST_CODE
        defaultApplicantAddresseShouldBeFound("postCode.contains=" + DEFAULT_POST_CODE);

        // Get all the applicantAddresseList where postCode contains UPDATED_POST_CODE
        defaultApplicantAddresseShouldNotBeFound("postCode.contains=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByPostCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where postCode does not contain DEFAULT_POST_CODE
        defaultApplicantAddresseShouldNotBeFound("postCode.doesNotContain=" + DEFAULT_POST_CODE);

        // Get all the applicantAddresseList where postCode does not contain UPDATED_POST_CODE
        defaultApplicantAddresseShouldBeFound("postCode.doesNotContain=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state equals to DEFAULT_STATE
        defaultApplicantAddresseShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the applicantAddresseList where state equals to UPDATED_STATE
        defaultApplicantAddresseShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state not equals to DEFAULT_STATE
        defaultApplicantAddresseShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the applicantAddresseList where state not equals to UPDATED_STATE
        defaultApplicantAddresseShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state in DEFAULT_STATE or UPDATED_STATE
        defaultApplicantAddresseShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the applicantAddresseList where state equals to UPDATED_STATE
        defaultApplicantAddresseShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state is not null
        defaultApplicantAddresseShouldBeFound("state.specified=true");

        // Get all the applicantAddresseList where state is null
        defaultApplicantAddresseShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state contains DEFAULT_STATE
        defaultApplicantAddresseShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the applicantAddresseList where state contains UPDATED_STATE
        defaultApplicantAddresseShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where state does not contain DEFAULT_STATE
        defaultApplicantAddresseShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the applicantAddresseList where state does not contain UPDATED_STATE
        defaultApplicantAddresseShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street equals to DEFAULT_STREET
        defaultApplicantAddresseShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the applicantAddresseList where street equals to UPDATED_STREET
        defaultApplicantAddresseShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street not equals to DEFAULT_STREET
        defaultApplicantAddresseShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the applicantAddresseList where street not equals to UPDATED_STREET
        defaultApplicantAddresseShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street in DEFAULT_STREET or UPDATED_STREET
        defaultApplicantAddresseShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the applicantAddresseList where street equals to UPDATED_STREET
        defaultApplicantAddresseShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street is not null
        defaultApplicantAddresseShouldBeFound("street.specified=true");

        // Get all the applicantAddresseList where street is null
        defaultApplicantAddresseShouldNotBeFound("street.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street contains DEFAULT_STREET
        defaultApplicantAddresseShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the applicantAddresseList where street contains UPDATED_STREET
        defaultApplicantAddresseShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where street does not contain DEFAULT_STREET
        defaultApplicantAddresseShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the applicantAddresseList where street does not contain UPDATED_STREET
        defaultApplicantAddresseShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet equals to DEFAULT_SUB_STREET
        defaultApplicantAddresseShouldBeFound("subStreet.equals=" + DEFAULT_SUB_STREET);

        // Get all the applicantAddresseList where subStreet equals to UPDATED_SUB_STREET
        defaultApplicantAddresseShouldNotBeFound("subStreet.equals=" + UPDATED_SUB_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet not equals to DEFAULT_SUB_STREET
        defaultApplicantAddresseShouldNotBeFound("subStreet.notEquals=" + DEFAULT_SUB_STREET);

        // Get all the applicantAddresseList where subStreet not equals to UPDATED_SUB_STREET
        defaultApplicantAddresseShouldBeFound("subStreet.notEquals=" + UPDATED_SUB_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet in DEFAULT_SUB_STREET or UPDATED_SUB_STREET
        defaultApplicantAddresseShouldBeFound("subStreet.in=" + DEFAULT_SUB_STREET + "," + UPDATED_SUB_STREET);

        // Get all the applicantAddresseList where subStreet equals to UPDATED_SUB_STREET
        defaultApplicantAddresseShouldNotBeFound("subStreet.in=" + UPDATED_SUB_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet is not null
        defaultApplicantAddresseShouldBeFound("subStreet.specified=true");

        // Get all the applicantAddresseList where subStreet is null
        defaultApplicantAddresseShouldNotBeFound("subStreet.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet contains DEFAULT_SUB_STREET
        defaultApplicantAddresseShouldBeFound("subStreet.contains=" + DEFAULT_SUB_STREET);

        // Get all the applicantAddresseList where subStreet contains UPDATED_SUB_STREET
        defaultApplicantAddresseShouldNotBeFound("subStreet.contains=" + UPDATED_SUB_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesBySubStreetNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where subStreet does not contain DEFAULT_SUB_STREET
        defaultApplicantAddresseShouldNotBeFound("subStreet.doesNotContain=" + DEFAULT_SUB_STREET);

        // Get all the applicantAddresseList where subStreet does not contain UPDATED_SUB_STREET
        defaultApplicantAddresseShouldBeFound("subStreet.doesNotContain=" + UPDATED_SUB_STREET);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town equals to DEFAULT_TOWN
        defaultApplicantAddresseShouldBeFound("town.equals=" + DEFAULT_TOWN);

        // Get all the applicantAddresseList where town equals to UPDATED_TOWN
        defaultApplicantAddresseShouldNotBeFound("town.equals=" + UPDATED_TOWN);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town not equals to DEFAULT_TOWN
        defaultApplicantAddresseShouldNotBeFound("town.notEquals=" + DEFAULT_TOWN);

        // Get all the applicantAddresseList where town not equals to UPDATED_TOWN
        defaultApplicantAddresseShouldBeFound("town.notEquals=" + UPDATED_TOWN);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town in DEFAULT_TOWN or UPDATED_TOWN
        defaultApplicantAddresseShouldBeFound("town.in=" + DEFAULT_TOWN + "," + UPDATED_TOWN);

        // Get all the applicantAddresseList where town equals to UPDATED_TOWN
        defaultApplicantAddresseShouldNotBeFound("town.in=" + UPDATED_TOWN);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town is not null
        defaultApplicantAddresseShouldBeFound("town.specified=true");

        // Get all the applicantAddresseList where town is null
        defaultApplicantAddresseShouldNotBeFound("town.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town contains DEFAULT_TOWN
        defaultApplicantAddresseShouldBeFound("town.contains=" + DEFAULT_TOWN);

        // Get all the applicantAddresseList where town contains UPDATED_TOWN
        defaultApplicantAddresseShouldNotBeFound("town.contains=" + UPDATED_TOWN);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByTownNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where town does not contain DEFAULT_TOWN
        defaultApplicantAddresseShouldNotBeFound("town.doesNotContain=" + DEFAULT_TOWN);

        // Get all the applicantAddresseList where town does not contain UPDATED_TOWN
        defaultApplicantAddresseShouldBeFound("town.doesNotContain=" + UPDATED_TOWN);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where enabled equals to DEFAULT_ENABLED
        defaultApplicantAddresseShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the applicantAddresseList where enabled equals to UPDATED_ENABLED
        defaultApplicantAddresseShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where enabled not equals to DEFAULT_ENABLED
        defaultApplicantAddresseShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the applicantAddresseList where enabled not equals to UPDATED_ENABLED
        defaultApplicantAddresseShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultApplicantAddresseShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the applicantAddresseList where enabled equals to UPDATED_ENABLED
        defaultApplicantAddresseShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        // Get all the applicantAddresseList where enabled is not null
        defaultApplicantAddresseShouldBeFound("enabled.specified=true");

        // Get all the applicantAddresseList where enabled is null
        defaultApplicantAddresseShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByAddresseCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);
        Country addresseCountry;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            addresseCountry = CountryResourceIT.createEntity(em);
            em.persist(addresseCountry);
            em.flush();
        } else {
            addresseCountry = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(addresseCountry);
        em.flush();
        applicantAddresse.setAddresseCountry(addresseCountry);
        applicantAddresseRepository.saveAndFlush(applicantAddresse);
        Long addresseCountryId = addresseCountry.getId();

        // Get all the applicantAddresseList where addresseCountry equals to addresseCountryId
        defaultApplicantAddresseShouldBeFound("addresseCountryId.equals=" + addresseCountryId);

        // Get all the applicantAddresseList where addresseCountry equals to (addresseCountryId + 1)
        defaultApplicantAddresseShouldNotBeFound("addresseCountryId.equals=" + (addresseCountryId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantAddressesByApplicantInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);
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
        applicantAddresse.addApplicantInfo(applicantInfo);
        applicantAddresseRepository.saveAndFlush(applicantAddresse);
        Long applicantInfoId = applicantInfo.getId();

        // Get all the applicantAddresseList where applicantInfo equals to applicantInfoId
        defaultApplicantAddresseShouldBeFound("applicantInfoId.equals=" + applicantInfoId);

        // Get all the applicantAddresseList where applicantInfo equals to (applicantInfoId + 1)
        defaultApplicantAddresseShouldNotBeFound("applicantInfoId.equals=" + (applicantInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantAddresseShouldBeFound(String filter) throws Exception {
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantAddresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].subStreet").value(hasItem(DEFAULT_SUB_STREET)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantAddresseShouldNotBeFound(String filter) throws Exception {
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantAddresseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicantAddresse() throws Exception {
        // Get the applicantAddresse
        restApplicantAddresseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicantAddresse() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();

        // Update the applicantAddresse
        ApplicantAddresse updatedApplicantAddresse = applicantAddresseRepository.findById(applicantAddresse.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantAddresse are not directly saved in db
        em.detach(updatedApplicantAddresse);
        updatedApplicantAddresse
            .postCode(UPDATED_POST_CODE)
            .state(UPDATED_STATE)
            .street(UPDATED_STREET)
            .subStreet(UPDATED_SUB_STREET)
            .town(UPDATED_TOWN)
            .enabled(UPDATED_ENABLED);
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(updatedApplicantAddresse);

        restApplicantAddresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantAddresseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
        ApplicantAddresse testApplicantAddresse = applicantAddresseList.get(applicantAddresseList.size() - 1);
        assertThat(testApplicantAddresse.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testApplicantAddresse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testApplicantAddresse.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testApplicantAddresse.getSubStreet()).isEqualTo(UPDATED_SUB_STREET);
        assertThat(testApplicantAddresse.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testApplicantAddresse.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void putNonExistingApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantAddresseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantAddresseWithPatch() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();

        // Update the applicantAddresse using partial update
        ApplicantAddresse partialUpdatedApplicantAddresse = new ApplicantAddresse();
        partialUpdatedApplicantAddresse.setId(applicantAddresse.getId());

        partialUpdatedApplicantAddresse.postCode(UPDATED_POST_CODE);

        restApplicantAddresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantAddresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantAddresse))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
        ApplicantAddresse testApplicantAddresse = applicantAddresseList.get(applicantAddresseList.size() - 1);
        assertThat(testApplicantAddresse.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testApplicantAddresse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testApplicantAddresse.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testApplicantAddresse.getSubStreet()).isEqualTo(DEFAULT_SUB_STREET);
        assertThat(testApplicantAddresse.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testApplicantAddresse.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    void fullUpdateApplicantAddresseWithPatch() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();

        // Update the applicantAddresse using partial update
        ApplicantAddresse partialUpdatedApplicantAddresse = new ApplicantAddresse();
        partialUpdatedApplicantAddresse.setId(applicantAddresse.getId());

        partialUpdatedApplicantAddresse
            .postCode(UPDATED_POST_CODE)
            .state(UPDATED_STATE)
            .street(UPDATED_STREET)
            .subStreet(UPDATED_SUB_STREET)
            .town(UPDATED_TOWN)
            .enabled(UPDATED_ENABLED);

        restApplicantAddresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantAddresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantAddresse))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
        ApplicantAddresse testApplicantAddresse = applicantAddresseList.get(applicantAddresseList.size() - 1);
        assertThat(testApplicantAddresse.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testApplicantAddresse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testApplicantAddresse.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testApplicantAddresse.getSubStreet()).isEqualTo(UPDATED_SUB_STREET);
        assertThat(testApplicantAddresse.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testApplicantAddresse.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void patchNonExistingApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantAddresseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicantAddresse() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddresseRepository.findAll().size();
        applicantAddresse.setId(count.incrementAndGet());

        // Create the ApplicantAddresse
        ApplicantAddresseDTO applicantAddresseDTO = applicantAddresseMapper.toDto(applicantAddresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantAddresseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantAddresseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantAddresse in the database
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicantAddresse() throws Exception {
        // Initialize the database
        applicantAddresseRepository.saveAndFlush(applicantAddresse);

        int databaseSizeBeforeDelete = applicantAddresseRepository.findAll().size();

        // Delete the applicantAddresse
        restApplicantAddresseMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicantAddresse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantAddresse> applicantAddresseList = applicantAddresseRepository.findAll();
        assertThat(applicantAddresseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
