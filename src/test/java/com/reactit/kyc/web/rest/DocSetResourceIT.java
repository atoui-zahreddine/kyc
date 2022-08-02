package com.reactit.kyc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.kyc.IntegrationTest;
import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.domain.Step;
import com.reactit.kyc.domain.enumeration.IdDocSetType;
import com.reactit.kyc.domain.enumeration.SubType;
import com.reactit.kyc.domain.enumeration.TypeDoc;
import com.reactit.kyc.repository.DocSetRepository;
import com.reactit.kyc.service.criteria.DocSetCriteria;
import com.reactit.kyc.service.dto.DocSetDTO;
import com.reactit.kyc.service.mapper.DocSetMapper;
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
 * Integration tests for the {@link DocSetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocSetResourceIT {

    private static final IdDocSetType DEFAULT_ID_DOC_SET_TYPE = IdDocSetType.IDENTITY;
    private static final IdDocSetType UPDATED_ID_DOC_SET_TYPE = IdDocSetType.SELFIE;

    private static final SubType DEFAULT_SUB_TYPES = SubType.FRONT_SIDE;
    private static final SubType UPDATED_SUB_TYPES = SubType.BACK_SIDE;

    private static final TypeDoc DEFAULT_TYPES = TypeDoc.ID_CARD;
    private static final TypeDoc UPDATED_TYPES = TypeDoc.PASSPORT;

    private static final String ENTITY_API_URL = "/api/doc-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocSetRepository docSetRepository;

    @Autowired
    private DocSetMapper docSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocSetMockMvc;

    private DocSet docSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocSet createEntity(EntityManager em) {
        DocSet docSet = new DocSet().idDocSetType(DEFAULT_ID_DOC_SET_TYPE).subTypes(DEFAULT_SUB_TYPES).types(DEFAULT_TYPES);
        return docSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocSet createUpdatedEntity(EntityManager em) {
        DocSet docSet = new DocSet().idDocSetType(UPDATED_ID_DOC_SET_TYPE).subTypes(UPDATED_SUB_TYPES).types(UPDATED_TYPES);
        return docSet;
    }

    @BeforeEach
    public void initTest() {
        docSet = createEntity(em);
    }

    @Test
    @Transactional
    void createDocSet() throws Exception {
        int databaseSizeBeforeCreate = docSetRepository.findAll().size();
        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);
        restDocSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docSetDTO)))
            .andExpect(status().isCreated());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeCreate + 1);
        DocSet testDocSet = docSetList.get(docSetList.size() - 1);
        assertThat(testDocSet.getIdDocSetType()).isEqualTo(DEFAULT_ID_DOC_SET_TYPE);
        assertThat(testDocSet.getSubTypes()).isEqualTo(DEFAULT_SUB_TYPES);
        assertThat(testDocSet.getTypes()).isEqualTo(DEFAULT_TYPES);
    }

    @Test
    @Transactional
    void createDocSetWithExistingId() throws Exception {
        // Create the DocSet with an existing ID
        docSet.setId(1L);
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        int databaseSizeBeforeCreate = docSetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocSets() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDocSetType").value(hasItem(DEFAULT_ID_DOC_SET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subTypes").value(hasItem(DEFAULT_SUB_TYPES.toString())))
            .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES.toString())));
    }

    @Test
    @Transactional
    void getDocSet() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get the docSet
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL_ID, docSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docSet.getId().intValue()))
            .andExpect(jsonPath("$.idDocSetType").value(DEFAULT_ID_DOC_SET_TYPE.toString()))
            .andExpect(jsonPath("$.subTypes").value(DEFAULT_SUB_TYPES.toString()))
            .andExpect(jsonPath("$.types").value(DEFAULT_TYPES.toString()));
    }

    @Test
    @Transactional
    void getDocSetsByIdFiltering() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        Long id = docSet.getId();

        defaultDocSetShouldBeFound("id.equals=" + id);
        defaultDocSetShouldNotBeFound("id.notEquals=" + id);

        defaultDocSetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocSetShouldNotBeFound("id.greaterThan=" + id);

        defaultDocSetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocSetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocSetsByIdDocSetTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where idDocSetType equals to DEFAULT_ID_DOC_SET_TYPE
        defaultDocSetShouldBeFound("idDocSetType.equals=" + DEFAULT_ID_DOC_SET_TYPE);

        // Get all the docSetList where idDocSetType equals to UPDATED_ID_DOC_SET_TYPE
        defaultDocSetShouldNotBeFound("idDocSetType.equals=" + UPDATED_ID_DOC_SET_TYPE);
    }

    @Test
    @Transactional
    void getAllDocSetsByIdDocSetTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where idDocSetType not equals to DEFAULT_ID_DOC_SET_TYPE
        defaultDocSetShouldNotBeFound("idDocSetType.notEquals=" + DEFAULT_ID_DOC_SET_TYPE);

        // Get all the docSetList where idDocSetType not equals to UPDATED_ID_DOC_SET_TYPE
        defaultDocSetShouldBeFound("idDocSetType.notEquals=" + UPDATED_ID_DOC_SET_TYPE);
    }

    @Test
    @Transactional
    void getAllDocSetsByIdDocSetTypeIsInShouldWork() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where idDocSetType in DEFAULT_ID_DOC_SET_TYPE or UPDATED_ID_DOC_SET_TYPE
        defaultDocSetShouldBeFound("idDocSetType.in=" + DEFAULT_ID_DOC_SET_TYPE + "," + UPDATED_ID_DOC_SET_TYPE);

        // Get all the docSetList where idDocSetType equals to UPDATED_ID_DOC_SET_TYPE
        defaultDocSetShouldNotBeFound("idDocSetType.in=" + UPDATED_ID_DOC_SET_TYPE);
    }

    @Test
    @Transactional
    void getAllDocSetsByIdDocSetTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where idDocSetType is not null
        defaultDocSetShouldBeFound("idDocSetType.specified=true");

        // Get all the docSetList where idDocSetType is null
        defaultDocSetShouldNotBeFound("idDocSetType.specified=false");
    }

    @Test
    @Transactional
    void getAllDocSetsBySubTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where subTypes equals to DEFAULT_SUB_TYPES
        defaultDocSetShouldBeFound("subTypes.equals=" + DEFAULT_SUB_TYPES);

        // Get all the docSetList where subTypes equals to UPDATED_SUB_TYPES
        defaultDocSetShouldNotBeFound("subTypes.equals=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsBySubTypesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where subTypes not equals to DEFAULT_SUB_TYPES
        defaultDocSetShouldNotBeFound("subTypes.notEquals=" + DEFAULT_SUB_TYPES);

        // Get all the docSetList where subTypes not equals to UPDATED_SUB_TYPES
        defaultDocSetShouldBeFound("subTypes.notEquals=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsBySubTypesIsInShouldWork() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where subTypes in DEFAULT_SUB_TYPES or UPDATED_SUB_TYPES
        defaultDocSetShouldBeFound("subTypes.in=" + DEFAULT_SUB_TYPES + "," + UPDATED_SUB_TYPES);

        // Get all the docSetList where subTypes equals to UPDATED_SUB_TYPES
        defaultDocSetShouldNotBeFound("subTypes.in=" + UPDATED_SUB_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsBySubTypesIsNullOrNotNull() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where subTypes is not null
        defaultDocSetShouldBeFound("subTypes.specified=true");

        // Get all the docSetList where subTypes is null
        defaultDocSetShouldNotBeFound("subTypes.specified=false");
    }

    @Test
    @Transactional
    void getAllDocSetsByTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where types equals to DEFAULT_TYPES
        defaultDocSetShouldBeFound("types.equals=" + DEFAULT_TYPES);

        // Get all the docSetList where types equals to UPDATED_TYPES
        defaultDocSetShouldNotBeFound("types.equals=" + UPDATED_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsByTypesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where types not equals to DEFAULT_TYPES
        defaultDocSetShouldNotBeFound("types.notEquals=" + DEFAULT_TYPES);

        // Get all the docSetList where types not equals to UPDATED_TYPES
        defaultDocSetShouldBeFound("types.notEquals=" + UPDATED_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsByTypesIsInShouldWork() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where types in DEFAULT_TYPES or UPDATED_TYPES
        defaultDocSetShouldBeFound("types.in=" + DEFAULT_TYPES + "," + UPDATED_TYPES);

        // Get all the docSetList where types equals to UPDATED_TYPES
        defaultDocSetShouldNotBeFound("types.in=" + UPDATED_TYPES);
    }

    @Test
    @Transactional
    void getAllDocSetsByTypesIsNullOrNotNull() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        // Get all the docSetList where types is not null
        defaultDocSetShouldBeFound("types.specified=true");

        // Get all the docSetList where types is null
        defaultDocSetShouldNotBeFound("types.specified=false");
    }

    @Test
    @Transactional
    void getAllDocSetsByStepIsEqualToSomething() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);
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
        docSet.setStep(step);
        docSetRepository.saveAndFlush(docSet);
        Long stepId = step.getId();

        // Get all the docSetList where step equals to stepId
        defaultDocSetShouldBeFound("stepId.equals=" + stepId);

        // Get all the docSetList where step equals to (stepId + 1)
        defaultDocSetShouldNotBeFound("stepId.equals=" + (stepId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocSetShouldBeFound(String filter) throws Exception {
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDocSetType").value(hasItem(DEFAULT_ID_DOC_SET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subTypes").value(hasItem(DEFAULT_SUB_TYPES.toString())))
            .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES.toString())));

        // Check, that the count call also returns 1
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocSetShouldNotBeFound(String filter) throws Exception {
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocSet() throws Exception {
        // Get the docSet
        restDocSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocSet() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();

        // Update the docSet
        DocSet updatedDocSet = docSetRepository.findById(docSet.getId()).get();
        // Disconnect from session so that the updates on updatedDocSet are not directly saved in db
        em.detach(updatedDocSet);
        updatedDocSet.idDocSetType(UPDATED_ID_DOC_SET_TYPE).subTypes(UPDATED_SUB_TYPES).types(UPDATED_TYPES);
        DocSetDTO docSetDTO = docSetMapper.toDto(updatedDocSet);

        restDocSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
        DocSet testDocSet = docSetList.get(docSetList.size() - 1);
        assertThat(testDocSet.getIdDocSetType()).isEqualTo(UPDATED_ID_DOC_SET_TYPE);
        assertThat(testDocSet.getSubTypes()).isEqualTo(UPDATED_SUB_TYPES);
        assertThat(testDocSet.getTypes()).isEqualTo(UPDATED_TYPES);
    }

    @Test
    @Transactional
    void putNonExistingDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docSetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocSetWithPatch() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();

        // Update the docSet using partial update
        DocSet partialUpdatedDocSet = new DocSet();
        partialUpdatedDocSet.setId(docSet.getId());

        partialUpdatedDocSet.idDocSetType(UPDATED_ID_DOC_SET_TYPE).types(UPDATED_TYPES);

        restDocSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocSet))
            )
            .andExpect(status().isOk());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
        DocSet testDocSet = docSetList.get(docSetList.size() - 1);
        assertThat(testDocSet.getIdDocSetType()).isEqualTo(UPDATED_ID_DOC_SET_TYPE);
        assertThat(testDocSet.getSubTypes()).isEqualTo(DEFAULT_SUB_TYPES);
        assertThat(testDocSet.getTypes()).isEqualTo(UPDATED_TYPES);
    }

    @Test
    @Transactional
    void fullUpdateDocSetWithPatch() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();

        // Update the docSet using partial update
        DocSet partialUpdatedDocSet = new DocSet();
        partialUpdatedDocSet.setId(docSet.getId());

        partialUpdatedDocSet.idDocSetType(UPDATED_ID_DOC_SET_TYPE).subTypes(UPDATED_SUB_TYPES).types(UPDATED_TYPES);

        restDocSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocSet))
            )
            .andExpect(status().isOk());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
        DocSet testDocSet = docSetList.get(docSetList.size() - 1);
        assertThat(testDocSet.getIdDocSetType()).isEqualTo(UPDATED_ID_DOC_SET_TYPE);
        assertThat(testDocSet.getSubTypes()).isEqualTo(UPDATED_SUB_TYPES);
        assertThat(testDocSet.getTypes()).isEqualTo(UPDATED_TYPES);
    }

    @Test
    @Transactional
    void patchNonExistingDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocSet() throws Exception {
        int databaseSizeBeforeUpdate = docSetRepository.findAll().size();
        docSet.setId(count.incrementAndGet());

        // Create the DocSet
        DocSetDTO docSetDTO = docSetMapper.toDto(docSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(docSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocSet in the database
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocSet() throws Exception {
        // Initialize the database
        docSetRepository.saveAndFlush(docSet);

        int databaseSizeBeforeDelete = docSetRepository.findAll().size();

        // Delete the docSet
        restDocSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, docSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocSet> docSetList = docSetRepository.findAll();
        assertThat(docSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
