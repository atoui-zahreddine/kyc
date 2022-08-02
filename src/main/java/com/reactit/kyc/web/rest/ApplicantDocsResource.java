package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.ApplicantDocsRepository;
import com.reactit.kyc.service.ApplicantDocsQueryService;
import com.reactit.kyc.service.ApplicantDocsService;
import com.reactit.kyc.service.criteria.ApplicantDocsCriteria;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.ApplicantDocs}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantDocsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantDocsResource.class);

    private static final String ENTITY_NAME = "applicantDocs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantDocsService applicantDocsService;

    private final ApplicantDocsRepository applicantDocsRepository;

    private final ApplicantDocsQueryService applicantDocsQueryService;

    public ApplicantDocsResource(
        ApplicantDocsService applicantDocsService,
        ApplicantDocsRepository applicantDocsRepository,
        ApplicantDocsQueryService applicantDocsQueryService
    ) {
        this.applicantDocsService = applicantDocsService;
        this.applicantDocsRepository = applicantDocsRepository;
        this.applicantDocsQueryService = applicantDocsQueryService;
    }

    /**
     * {@code POST  /applicant-docs} : Create a new applicantDocs.
     *
     * @param applicantDocsDTO the applicantDocsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantDocsDTO, or with status {@code 400 (Bad Request)} if the applicantDocs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-docs")
    public ResponseEntity<ApplicantDocsDTO> createApplicantDocs(@RequestBody ApplicantDocsDTO applicantDocsDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicantDocs : {}", applicantDocsDTO);
        if (applicantDocsDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantDocs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantDocsDTO result = applicantDocsService.save(applicantDocsDTO);
        return ResponseEntity
            .created(new URI("/api/applicant-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-docs/:id} : Updates an existing applicantDocs.
     *
     * @param id the id of the applicantDocsDTO to save.
     * @param applicantDocsDTO the applicantDocsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantDocsDTO,
     * or with status {@code 400 (Bad Request)} if the applicantDocsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantDocsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-docs/{id}")
    public ResponseEntity<ApplicantDocsDTO> updateApplicantDocs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantDocsDTO applicantDocsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicantDocs : {}, {}", id, applicantDocsDTO);
        if (applicantDocsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantDocsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantDocsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicantDocsDTO result = applicantDocsService.save(applicantDocsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantDocsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicant-docs/:id} : Partial updates given fields of an existing applicantDocs, field will ignore if it is null
     *
     * @param id the id of the applicantDocsDTO to save.
     * @param applicantDocsDTO the applicantDocsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantDocsDTO,
     * or with status {@code 400 (Bad Request)} if the applicantDocsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicantDocsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicantDocsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicant-docs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicantDocsDTO> partialUpdateApplicantDocs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantDocsDTO applicantDocsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicantDocs partially : {}, {}", id, applicantDocsDTO);
        if (applicantDocsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantDocsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantDocsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicantDocsDTO> result = applicantDocsService.partialUpdate(applicantDocsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantDocsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applicant-docs} : get all the applicantDocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantDocs in body.
     */
    @GetMapping("/applicant-docs")
    public ResponseEntity<List<ApplicantDocsDTO>> getAllApplicantDocs(
        ApplicantDocsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicantDocs by criteria: {}", criteria);
        Page<ApplicantDocsDTO> page = applicantDocsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-docs/count} : count all the applicantDocs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-docs/count")
    public ResponseEntity<Long> countApplicantDocs(ApplicantDocsCriteria criteria) {
        log.debug("REST request to count ApplicantDocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantDocsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-docs/:id} : get the "id" applicantDocs.
     *
     * @param id the id of the applicantDocsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantDocsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-docs/{id}")
    public ResponseEntity<ApplicantDocsDTO> getApplicantDocs(@PathVariable Long id) {
        log.debug("REST request to get ApplicantDocs : {}", id);
        Optional<ApplicantDocsDTO> applicantDocsDTO = applicantDocsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantDocsDTO);
    }

    /**
     * {@code DELETE  /applicant-docs/:id} : delete the "id" applicantDocs.
     *
     * @param id the id of the applicantDocsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-docs/{id}")
    public ResponseEntity<Void> deleteApplicantDocs(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantDocs : {}", id);
        applicantDocsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
