package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.domain.Country;
import com.reactit.kyc.domain.enumeration.CountryRegion;
import com.reactit.kyc.repository.CountryRepository;
import com.reactit.kyc.service.criteria.CountryCriteria;
import com.reactit.kyc.service.dto.CountryDTO;
import com.reactit.kyc.service.mapper.CountryMapper;
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
 * Integration tests for the {@link CountryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_2 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE_3 = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_CODE = "BBBBBBBBBB";

    private static final CountryRegion DEFAULT_REGION = CountryRegion.Africa;
    private static final CountryRegion UPDATED_REGION = CountryRegion.Americas;

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .name(DEFAULT_NAME)
            .countryCode2(DEFAULT_COUNTRY_CODE_2)
            .countryCode3(DEFAULT_COUNTRY_CODE_3)
            .phoneCode(DEFAULT_PHONE_CODE)
            .region(DEFAULT_REGION);
        return country;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .name(UPDATED_NAME)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .phoneCode(UPDATED_PHONE_CODE)
            .region(UPDATED_REGION);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getCountryCode2()).isEqualTo(DEFAULT_COUNTRY_CODE_2);
        assertThat(testCountry.getCountryCode3()).isEqualTo(DEFAULT_COUNTRY_CODE_3);
        assertThat(testCountry.getPhoneCode()).isEqualTo(DEFAULT_PHONE_CODE);
        assertThat(testCountry.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void createCountryWithExistingId() throws Exception {
        // Create the Country with an existing ID
        country.setId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].countryCode2").value(hasItem(DEFAULT_COUNTRY_CODE_2)))
            .andExpect(jsonPath("$.[*].countryCode3").value(hasItem(DEFAULT_COUNTRY_CODE_3)))
            .andExpect(jsonPath("$.[*].phoneCode").value(hasItem(DEFAULT_PHONE_CODE)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())));
    }

    @Test
    @Transactional
    void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc
            .perform(get(ENTITY_API_URL_ID, country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.countryCode2").value(DEFAULT_COUNTRY_CODE_2))
            .andExpect(jsonPath("$.countryCode3").value(DEFAULT_COUNTRY_CODE_3))
            .andExpect(jsonPath("$.phoneCode").value(DEFAULT_PHONE_CODE))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }

    @Test
    @Transactional
    void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        Long id = country.getId();

        defaultCountryShouldBeFound("id.equals=" + id);
        defaultCountryShouldNotBeFound("id.notEquals=" + id);

        defaultCountryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.greaterThan=" + id);

        defaultCountryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name equals to DEFAULT_NAME
        defaultCountryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name not equals to DEFAULT_NAME
        defaultCountryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countryList where name not equals to UPDATED_NAME
        defaultCountryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name is not null
        defaultCountryShouldBeFound("name.specified=true");

        // Get all the countryList where name is null
        defaultCountryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByNameContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name contains DEFAULT_NAME
        defaultCountryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countryList where name contains UPDATED_NAME
        defaultCountryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name does not contain DEFAULT_NAME
        defaultCountryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countryList where name does not contain UPDATED_NAME
        defaultCountryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 equals to DEFAULT_COUNTRY_CODE_2
        defaultCountryShouldBeFound("countryCode2.equals=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the countryList where countryCode2 equals to UPDATED_COUNTRY_CODE_2
        defaultCountryShouldNotBeFound("countryCode2.equals=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 not equals to DEFAULT_COUNTRY_CODE_2
        defaultCountryShouldNotBeFound("countryCode2.notEquals=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the countryList where countryCode2 not equals to UPDATED_COUNTRY_CODE_2
        defaultCountryShouldBeFound("countryCode2.notEquals=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 in DEFAULT_COUNTRY_CODE_2 or UPDATED_COUNTRY_CODE_2
        defaultCountryShouldBeFound("countryCode2.in=" + DEFAULT_COUNTRY_CODE_2 + "," + UPDATED_COUNTRY_CODE_2);

        // Get all the countryList where countryCode2 equals to UPDATED_COUNTRY_CODE_2
        defaultCountryShouldNotBeFound("countryCode2.in=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 is not null
        defaultCountryShouldBeFound("countryCode2.specified=true");

        // Get all the countryList where countryCode2 is null
        defaultCountryShouldNotBeFound("countryCode2.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 contains DEFAULT_COUNTRY_CODE_2
        defaultCountryShouldBeFound("countryCode2.contains=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the countryList where countryCode2 contains UPDATED_COUNTRY_CODE_2
        defaultCountryShouldNotBeFound("countryCode2.contains=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode2NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode2 does not contain DEFAULT_COUNTRY_CODE_2
        defaultCountryShouldNotBeFound("countryCode2.doesNotContain=" + DEFAULT_COUNTRY_CODE_2);

        // Get all the countryList where countryCode2 does not contain UPDATED_COUNTRY_CODE_2
        defaultCountryShouldBeFound("countryCode2.doesNotContain=" + UPDATED_COUNTRY_CODE_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 equals to DEFAULT_COUNTRY_CODE_3
        defaultCountryShouldBeFound("countryCode3.equals=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the countryList where countryCode3 equals to UPDATED_COUNTRY_CODE_3
        defaultCountryShouldNotBeFound("countryCode3.equals=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 not equals to DEFAULT_COUNTRY_CODE_3
        defaultCountryShouldNotBeFound("countryCode3.notEquals=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the countryList where countryCode3 not equals to UPDATED_COUNTRY_CODE_3
        defaultCountryShouldBeFound("countryCode3.notEquals=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 in DEFAULT_COUNTRY_CODE_3 or UPDATED_COUNTRY_CODE_3
        defaultCountryShouldBeFound("countryCode3.in=" + DEFAULT_COUNTRY_CODE_3 + "," + UPDATED_COUNTRY_CODE_3);

        // Get all the countryList where countryCode3 equals to UPDATED_COUNTRY_CODE_3
        defaultCountryShouldNotBeFound("countryCode3.in=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 is not null
        defaultCountryShouldBeFound("countryCode3.specified=true");

        // Get all the countryList where countryCode3 is null
        defaultCountryShouldNotBeFound("countryCode3.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 contains DEFAULT_COUNTRY_CODE_3
        defaultCountryShouldBeFound("countryCode3.contains=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the countryList where countryCode3 contains UPDATED_COUNTRY_CODE_3
        defaultCountryShouldNotBeFound("countryCode3.contains=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryCode3NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryCode3 does not contain DEFAULT_COUNTRY_CODE_3
        defaultCountryShouldNotBeFound("countryCode3.doesNotContain=" + DEFAULT_COUNTRY_CODE_3);

        // Get all the countryList where countryCode3 does not contain UPDATED_COUNTRY_CODE_3
        defaultCountryShouldBeFound("countryCode3.doesNotContain=" + UPDATED_COUNTRY_CODE_3);
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode equals to DEFAULT_PHONE_CODE
        defaultCountryShouldBeFound("phoneCode.equals=" + DEFAULT_PHONE_CODE);

        // Get all the countryList where phoneCode equals to UPDATED_PHONE_CODE
        defaultCountryShouldNotBeFound("phoneCode.equals=" + UPDATED_PHONE_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode not equals to DEFAULT_PHONE_CODE
        defaultCountryShouldNotBeFound("phoneCode.notEquals=" + DEFAULT_PHONE_CODE);

        // Get all the countryList where phoneCode not equals to UPDATED_PHONE_CODE
        defaultCountryShouldBeFound("phoneCode.notEquals=" + UPDATED_PHONE_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode in DEFAULT_PHONE_CODE or UPDATED_PHONE_CODE
        defaultCountryShouldBeFound("phoneCode.in=" + DEFAULT_PHONE_CODE + "," + UPDATED_PHONE_CODE);

        // Get all the countryList where phoneCode equals to UPDATED_PHONE_CODE
        defaultCountryShouldNotBeFound("phoneCode.in=" + UPDATED_PHONE_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode is not null
        defaultCountryShouldBeFound("phoneCode.specified=true");

        // Get all the countryList where phoneCode is null
        defaultCountryShouldNotBeFound("phoneCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode contains DEFAULT_PHONE_CODE
        defaultCountryShouldBeFound("phoneCode.contains=" + DEFAULT_PHONE_CODE);

        // Get all the countryList where phoneCode contains UPDATED_PHONE_CODE
        defaultCountryShouldNotBeFound("phoneCode.contains=" + UPDATED_PHONE_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByPhoneCodeNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where phoneCode does not contain DEFAULT_PHONE_CODE
        defaultCountryShouldNotBeFound("phoneCode.doesNotContain=" + DEFAULT_PHONE_CODE);

        // Get all the countryList where phoneCode does not contain UPDATED_PHONE_CODE
        defaultCountryShouldBeFound("phoneCode.doesNotContain=" + UPDATED_PHONE_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region equals to DEFAULT_REGION
        defaultCountryShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the countryList where region equals to UPDATED_REGION
        defaultCountryShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllCountriesByRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region not equals to DEFAULT_REGION
        defaultCountryShouldNotBeFound("region.notEquals=" + DEFAULT_REGION);

        // Get all the countryList where region not equals to UPDATED_REGION
        defaultCountryShouldBeFound("region.notEquals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllCountriesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region in DEFAULT_REGION or UPDATED_REGION
        defaultCountryShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the countryList where region equals to UPDATED_REGION
        defaultCountryShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllCountriesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region is not null
        defaultCountryShouldBeFound("region.specified=true");

        // Get all the countryList where region is null
        defaultCountryShouldNotBeFound("region.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByAddressesIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        ApplicantAddresse addresses;
        if (TestUtil.findAll(em, ApplicantAddresse.class).isEmpty()) {
            addresses = ApplicantAddresseResourceIT.createEntity(em);
            em.persist(addresses);
            em.flush();
        } else {
            addresses = TestUtil.findAll(em, ApplicantAddresse.class).get(0);
        }
        em.persist(addresses);
        em.flush();
        country.addAddresses(addresses);
        countryRepository.saveAndFlush(country);
        Long addressesId = addresses.getId();

        // Get all the countryList where addresses equals to addressesId
        defaultCountryShouldBeFound("addressesId.equals=" + addressesId);

        // Get all the countryList where addresses equals to (addressesId + 1)
        defaultCountryShouldNotBeFound("addressesId.equals=" + (addressesId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByDocsIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        ApplicantDocs docs;
        if (TestUtil.findAll(em, ApplicantDocs.class).isEmpty()) {
            docs = ApplicantDocsResourceIT.createEntity(em);
            em.persist(docs);
            em.flush();
        } else {
            docs = TestUtil.findAll(em, ApplicantDocs.class).get(0);
        }
        em.persist(docs);
        em.flush();
        country.addDocs(docs);
        countryRepository.saveAndFlush(country);
        Long docsId = docs.getId();

        // Get all the countryList where docs equals to docsId
        defaultCountryShouldBeFound("docsId.equals=" + docsId);

        // Get all the countryList where docs equals to (docsId + 1)
        defaultCountryShouldNotBeFound("docsId.equals=" + (docsId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByApplicantsIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        ApplicantInfo applicants;
        if (TestUtil.findAll(em, ApplicantInfo.class).isEmpty()) {
            applicants = ApplicantInfoResourceIT.createEntity(em);
            em.persist(applicants);
            em.flush();
        } else {
            applicants = TestUtil.findAll(em, ApplicantInfo.class).get(0);
        }
        em.persist(applicants);
        em.flush();
        country.addApplicants(applicants);
        countryRepository.saveAndFlush(country);
        Long applicantsId = applicants.getId();

        // Get all the countryList where applicants equals to applicantsId
        defaultCountryShouldBeFound("applicantsId.equals=" + applicantsId);

        // Get all the countryList where applicants equals to (applicantsId + 1)
        defaultCountryShouldNotBeFound("applicantsId.equals=" + (applicantsId + 1));
    }

    @Test
    @Transactional
    void getAllCountriesByPhonesIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);
        ApplicantPhone phones;
        if (TestUtil.findAll(em, ApplicantPhone.class).isEmpty()) {
            phones = ApplicantPhoneResourceIT.createEntity(em);
            em.persist(phones);
            em.flush();
        } else {
            phones = TestUtil.findAll(em, ApplicantPhone.class).get(0);
        }
        em.persist(phones);
        em.flush();
        country.addPhones(phones);
        countryRepository.saveAndFlush(country);
        Long phonesId = phones.getId();

        // Get all the countryList where phones equals to phonesId
        defaultCountryShouldBeFound("phonesId.equals=" + phonesId);

        // Get all the countryList where phones equals to (phonesId + 1)
        defaultCountryShouldNotBeFound("phonesId.equals=" + (phonesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].countryCode2").value(hasItem(DEFAULT_COUNTRY_CODE_2)))
            .andExpect(jsonPath("$.[*].countryCode3").value(hasItem(DEFAULT_COUNTRY_CODE_3)))
            .andExpect(jsonPath("$.[*].phoneCode").value(hasItem(DEFAULT_PHONE_CODE)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())));

        // Check, that the count call also returns 1
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .name(UPDATED_NAME)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .phoneCode(UPDATED_PHONE_CODE)
            .region(UPDATED_REGION);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCountryCode2()).isEqualTo(UPDATED_COUNTRY_CODE_2);
        assertThat(testCountry.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testCountry.getPhoneCode()).isEqualTo(UPDATED_PHONE_CODE);
        assertThat(testCountry.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void putNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setId(country.getId());

        partialUpdatedCountry
            .name(UPDATED_NAME)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .phoneCode(UPDATED_PHONE_CODE);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCountryCode2()).isEqualTo(UPDATED_COUNTRY_CODE_2);
        assertThat(testCountry.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testCountry.getPhoneCode()).isEqualTo(UPDATED_PHONE_CODE);
        assertThat(testCountry.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void fullUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setId(country.getId());

        partialUpdatedCountry
            .name(UPDATED_NAME)
            .countryCode2(UPDATED_COUNTRY_CODE_2)
            .countryCode3(UPDATED_COUNTRY_CODE_3)
            .phoneCode(UPDATED_PHONE_CODE)
            .region(UPDATED_REGION);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCountryCode2()).isEqualTo(UPDATED_COUNTRY_CODE_2);
        assertThat(testCountry.getCountryCode3()).isEqualTo(UPDATED_COUNTRY_CODE_3);
        assertThat(testCountry.getPhoneCode()).isEqualTo(UPDATED_PHONE_CODE);
        assertThat(testCountry.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void patchNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(countryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc
            .perform(delete(ENTITY_API_URL_ID, country.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
