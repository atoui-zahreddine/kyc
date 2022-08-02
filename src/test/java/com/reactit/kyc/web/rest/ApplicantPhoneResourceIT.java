package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.repository.ApplicantPhoneRepository;
import com.reactit.kyc.service.criteria.ApplicantPhoneCriteria;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import com.reactit.kyc.service.mapper.ApplicantPhoneMapper;
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
 * Integration tests for the {@link ApplicantPhoneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicantPhoneResourceIT {

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/applicant-phones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicantPhoneRepository applicantPhoneRepository;

    @Autowired
    private ApplicantPhoneMapper applicantPhoneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantPhoneMockMvc;

    private ApplicantPhone applicantPhone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantPhone createEntity(EntityManager em) {
        ApplicantPhone applicantPhone = new ApplicantPhone().country(DEFAULT_COUNTRY).number(DEFAULT_NUMBER).enabled(DEFAULT_ENABLED);
        return applicantPhone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantPhone createUpdatedEntity(EntityManager em) {
        ApplicantPhone applicantPhone = new ApplicantPhone().country(UPDATED_COUNTRY).number(UPDATED_NUMBER).enabled(UPDATED_ENABLED);
        return applicantPhone;
    }

    @BeforeEach
    public void initTest() {
        applicantPhone = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicantPhone() throws Exception {
        int databaseSizeBeforeCreate = applicantPhoneRepository.findAll().size();
        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);
        restApplicantPhoneMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantPhone testApplicantPhone = applicantPhoneList.get(applicantPhoneList.size() - 1);
        assertThat(testApplicantPhone.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testApplicantPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testApplicantPhone.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    void createApplicantPhoneWithExistingId() throws Exception {
        // Create the ApplicantPhone with an existing ID
        applicantPhone.setId(1L);
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        int databaseSizeBeforeCreate = applicantPhoneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantPhoneMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicantPhones() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    void getApplicantPhone() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get the applicantPhone
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL_ID, applicantPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantPhone.getId().intValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    void getApplicantPhonesByIdFiltering() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        Long id = applicantPhone.getId();

        defaultApplicantPhoneShouldBeFound("id.equals=" + id);
        defaultApplicantPhoneShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantPhoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantPhoneShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantPhoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantPhoneShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country equals to DEFAULT_COUNTRY
        defaultApplicantPhoneShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the applicantPhoneList where country equals to UPDATED_COUNTRY
        defaultApplicantPhoneShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country not equals to DEFAULT_COUNTRY
        defaultApplicantPhoneShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the applicantPhoneList where country not equals to UPDATED_COUNTRY
        defaultApplicantPhoneShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultApplicantPhoneShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the applicantPhoneList where country equals to UPDATED_COUNTRY
        defaultApplicantPhoneShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country is not null
        defaultApplicantPhoneShouldBeFound("country.specified=true");

        // Get all the applicantPhoneList where country is null
        defaultApplicantPhoneShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryContainsSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country contains DEFAULT_COUNTRY
        defaultApplicantPhoneShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the applicantPhoneList where country contains UPDATED_COUNTRY
        defaultApplicantPhoneShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where country does not contain DEFAULT_COUNTRY
        defaultApplicantPhoneShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the applicantPhoneList where country does not contain UPDATED_COUNTRY
        defaultApplicantPhoneShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number equals to DEFAULT_NUMBER
        defaultApplicantPhoneShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the applicantPhoneList where number equals to UPDATED_NUMBER
        defaultApplicantPhoneShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number not equals to DEFAULT_NUMBER
        defaultApplicantPhoneShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the applicantPhoneList where number not equals to UPDATED_NUMBER
        defaultApplicantPhoneShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultApplicantPhoneShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the applicantPhoneList where number equals to UPDATED_NUMBER
        defaultApplicantPhoneShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number is not null
        defaultApplicantPhoneShouldBeFound("number.specified=true");

        // Get all the applicantPhoneList where number is null
        defaultApplicantPhoneShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberContainsSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number contains DEFAULT_NUMBER
        defaultApplicantPhoneShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the applicantPhoneList where number contains UPDATED_NUMBER
        defaultApplicantPhoneShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where number does not contain DEFAULT_NUMBER
        defaultApplicantPhoneShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the applicantPhoneList where number does not contain UPDATED_NUMBER
        defaultApplicantPhoneShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where enabled equals to DEFAULT_ENABLED
        defaultApplicantPhoneShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the applicantPhoneList where enabled equals to UPDATED_ENABLED
        defaultApplicantPhoneShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where enabled not equals to DEFAULT_ENABLED
        defaultApplicantPhoneShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the applicantPhoneList where enabled not equals to UPDATED_ENABLED
        defaultApplicantPhoneShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultApplicantPhoneShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the applicantPhoneList where enabled equals to UPDATED_ENABLED
        defaultApplicantPhoneShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        // Get all the applicantPhoneList where enabled is not null
        defaultApplicantPhoneShouldBeFound("enabled.specified=true");

        // Get all the applicantPhoneList where enabled is null
        defaultApplicantPhoneShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByApplicantInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);
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
        applicantPhone.setApplicantInfo(applicantInfo);
        applicantPhoneRepository.saveAndFlush(applicantPhone);
        Long applicantInfoId = applicantInfo.getId();

        // Get all the applicantPhoneList where applicantInfo equals to applicantInfoId
        defaultApplicantPhoneShouldBeFound("applicantInfoId.equals=" + applicantInfoId);

        // Get all the applicantPhoneList where applicantInfo equals to (applicantInfoId + 1)
        defaultApplicantPhoneShouldNotBeFound("applicantInfoId.equals=" + (applicantInfoId + 1));
    }

    @Test
    @Transactional
    void getAllApplicantPhonesByPhoneCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);
        Country phoneCountry;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            phoneCountry = CountryResourceIT.createEntity(em);
            em.persist(phoneCountry);
            em.flush();
        } else {
            phoneCountry = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(phoneCountry);
        em.flush();
        applicantPhone.addPhoneCountry(phoneCountry);
        applicantPhoneRepository.saveAndFlush(applicantPhone);
        Long phoneCountryId = phoneCountry.getId();

        // Get all the applicantPhoneList where phoneCountry equals to phoneCountryId
        defaultApplicantPhoneShouldBeFound("phoneCountryId.equals=" + phoneCountryId);

        // Get all the applicantPhoneList where phoneCountry equals to (phoneCountryId + 1)
        defaultApplicantPhoneShouldNotBeFound("phoneCountryId.equals=" + (phoneCountryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantPhoneShouldBeFound(String filter) throws Exception {
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantPhoneShouldNotBeFound(String filter) throws Exception {
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantPhoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicantPhone() throws Exception {
        // Get the applicantPhone
        restApplicantPhoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicantPhone() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();

        // Update the applicantPhone
        ApplicantPhone updatedApplicantPhone = applicantPhoneRepository.findById(applicantPhone.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantPhone are not directly saved in db
        em.detach(updatedApplicantPhone);
        updatedApplicantPhone.country(UPDATED_COUNTRY).number(UPDATED_NUMBER).enabled(UPDATED_ENABLED);
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(updatedApplicantPhone);

        restApplicantPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantPhoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
        ApplicantPhone testApplicantPhone = applicantPhoneList.get(applicantPhoneList.size() - 1);
        assertThat(testApplicantPhone.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testApplicantPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testApplicantPhone.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void putNonExistingApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicantPhoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicantPhoneWithPatch() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();

        // Update the applicantPhone using partial update
        ApplicantPhone partialUpdatedApplicantPhone = new ApplicantPhone();
        partialUpdatedApplicantPhone.setId(applicantPhone.getId());

        restApplicantPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantPhone))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
        ApplicantPhone testApplicantPhone = applicantPhoneList.get(applicantPhoneList.size() - 1);
        assertThat(testApplicantPhone.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testApplicantPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testApplicantPhone.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    void fullUpdateApplicantPhoneWithPatch() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();

        // Update the applicantPhone using partial update
        ApplicantPhone partialUpdatedApplicantPhone = new ApplicantPhone();
        partialUpdatedApplicantPhone.setId(applicantPhone.getId());

        partialUpdatedApplicantPhone.country(UPDATED_COUNTRY).number(UPDATED_NUMBER).enabled(UPDATED_ENABLED);

        restApplicantPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicantPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicantPhone))
            )
            .andExpect(status().isOk());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
        ApplicantPhone testApplicantPhone = applicantPhoneList.get(applicantPhoneList.size() - 1);
        assertThat(testApplicantPhone.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testApplicantPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testApplicantPhone.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void patchNonExistingApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicantPhoneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicantPhone() throws Exception {
        int databaseSizeBeforeUpdate = applicantPhoneRepository.findAll().size();
        applicantPhone.setId(count.incrementAndGet());

        // Create the ApplicantPhone
        ApplicantPhoneDTO applicantPhoneDTO = applicantPhoneMapper.toDto(applicantPhone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicantPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicantPhoneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicantPhone in the database
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicantPhone() throws Exception {
        // Initialize the database
        applicantPhoneRepository.saveAndFlush(applicantPhone);

        int databaseSizeBeforeDelete = applicantPhoneRepository.findAll().size();

        // Delete the applicantPhone
        restApplicantPhoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicantPhone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantPhone> applicantPhoneList = applicantPhoneRepository.findAll();
        assertThat(applicantPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
