package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.ApplicantPhoneRepository;
import com.reactit.kyc.service.ApplicantPhoneQueryService;
import com.reactit.kyc.service.ApplicantPhoneService;
import com.reactit.kyc.service.criteria.ApplicantPhoneCriteria;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import com.reactit.kyc.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reactit.kyc.domain.ApplicantPhone}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantPhoneResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantPhoneResource.class);

    private static final String ENTITY_NAME = "applicantPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantPhoneService applicantPhoneService;

    private final ApplicantPhoneRepository applicantPhoneRepository;

    private final ApplicantPhoneQueryService applicantPhoneQueryService;

    public ApplicantPhoneResource(
        ApplicantPhoneService applicantPhoneService,
        ApplicantPhoneRepository applicantPhoneRepository,
        ApplicantPhoneQueryService applicantPhoneQueryService
    ) {
        this.applicantPhoneService = applicantPhoneService;
        this.applicantPhoneRepository = applicantPhoneRepository;
        this.applicantPhoneQueryService = applicantPhoneQueryService;
    }

    /**
     * {@code POST  /applicant-phones} : Create a new applicantPhone.
     *
     * @param applicantPhoneDTO the applicantPhoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantPhoneDTO, or with status {@code 400 (Bad Request)} if the applicantPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-phones")
    public ResponseEntity<ApplicantPhoneDTO> createApplicantPhone(@RequestBody ApplicantPhoneDTO applicantPhoneDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApplicantPhone : {}", applicantPhoneDTO);
        if (applicantPhoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantPhoneDTO result = applicantPhoneService.save(applicantPhoneDTO);
        return ResponseEntity
            .created(new URI("/api/applicant-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-phones/:id} : Updates an existing applicantPhone.
     *
     * @param id the id of the applicantPhoneDTO to save.
     * @param applicantPhoneDTO the applicantPhoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantPhoneDTO,
     * or with status {@code 400 (Bad Request)} if the applicantPhoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantPhoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-phones/{id}")
    public ResponseEntity<ApplicantPhoneDTO> updateApplicantPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantPhoneDTO applicantPhoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicantPhone : {}, {}", id, applicantPhoneDTO);
        if (applicantPhoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantPhoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicantPhoneDTO result = applicantPhoneService.save(applicantPhoneDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicant-phones/:id} : Partial updates given fields of an existing applicantPhone, field will ignore if it is null
     *
     * @param id the id of the applicantPhoneDTO to save.
     * @param applicantPhoneDTO the applicantPhoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantPhoneDTO,
     * or with status {@code 400 (Bad Request)} if the applicantPhoneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicantPhoneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicantPhoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicant-phones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicantPhoneDTO> partialUpdateApplicantPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantPhoneDTO applicantPhoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicantPhone partially : {}, {}", id, applicantPhoneDTO);
        if (applicantPhoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantPhoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicantPhoneDTO> result = applicantPhoneService.partialUpdate(applicantPhoneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantPhoneDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applicant-phones} : get all the applicantPhones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantPhones in body.
     */
    @GetMapping("/applicant-phones")
    public ResponseEntity<List<ApplicantPhoneDTO>> getAllApplicantPhones(
        ApplicantPhoneCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicantPhones by criteria: {}", criteria);
        Page<ApplicantPhoneDTO> page = applicantPhoneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-phones/count} : count all the applicantPhones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-phones/count")
    public ResponseEntity<Long> countApplicantPhones(ApplicantPhoneCriteria criteria) {
        log.debug("REST request to count ApplicantPhones by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantPhoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-phones/:id} : get the "id" applicantPhone.
     *
     * @param id the id of the applicantPhoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantPhoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-phones/{id}")
    public ResponseEntity<ApplicantPhoneDTO> getApplicantPhone(@PathVariable Long id) {
        log.debug("REST request to get ApplicantPhone : {}", id);
        Optional<ApplicantPhoneDTO> applicantPhoneDTO = applicantPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantPhoneDTO);
    }

    /**
     * {@code DELETE  /applicant-phones/:id} : delete the "id" applicantPhone.
     *
     * @param id the id of the applicantPhoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-phones/{id}")
    public ResponseEntity<Void> deleteApplicantPhone(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantPhone : {}", id);
        applicantPhoneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
