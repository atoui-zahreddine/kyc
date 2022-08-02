package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.ApplicantLevelRepository;
import com.reactit.kyc.service.ApplicantLevelQueryService;
import com.reactit.kyc.service.ApplicantLevelService;
import com.reactit.kyc.service.criteria.ApplicantLevelCriteria;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.ApplicantLevel}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantLevelResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantLevelResource.class);

    private static final String ENTITY_NAME = "applicantLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantLevelService applicantLevelService;

    private final ApplicantLevelRepository applicantLevelRepository;

    private final ApplicantLevelQueryService applicantLevelQueryService;

    public ApplicantLevelResource(
        ApplicantLevelService applicantLevelService,
        ApplicantLevelRepository applicantLevelRepository,
        ApplicantLevelQueryService applicantLevelQueryService
    ) {
        this.applicantLevelService = applicantLevelService;
        this.applicantLevelRepository = applicantLevelRepository;
        this.applicantLevelQueryService = applicantLevelQueryService;
    }

    /**
     * {@code POST  /applicant-levels} : Create a new applicantLevel.
     *
     * @param applicantLevelDTO the applicantLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantLevelDTO, or with status {@code 400 (Bad Request)} if the applicantLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-levels")
    public ResponseEntity<ApplicantLevelDTO> createApplicantLevel(@RequestBody ApplicantLevelDTO applicantLevelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApplicantLevel : {}", applicantLevelDTO);
        if (applicantLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantLevelDTO result = applicantLevelService.save(applicantLevelDTO);
        return ResponseEntity
            .created(new URI("/api/applicant-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-levels/:id} : Updates an existing applicantLevel.
     *
     * @param id the id of the applicantLevelDTO to save.
     * @param applicantLevelDTO the applicantLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantLevelDTO,
     * or with status {@code 400 (Bad Request)} if the applicantLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-levels/{id}")
    public ResponseEntity<ApplicantLevelDTO> updateApplicantLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantLevelDTO applicantLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicantLevel : {}, {}", id, applicantLevelDTO);
        if (applicantLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicantLevelDTO result = applicantLevelService.save(applicantLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicant-levels/:id} : Partial updates given fields of an existing applicantLevel, field will ignore if it is null
     *
     * @param id the id of the applicantLevelDTO to save.
     * @param applicantLevelDTO the applicantLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantLevelDTO,
     * or with status {@code 400 (Bad Request)} if the applicantLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicantLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicantLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicant-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicantLevelDTO> partialUpdateApplicantLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantLevelDTO applicantLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicantLevel partially : {}, {}", id, applicantLevelDTO);
        if (applicantLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicantLevelDTO> result = applicantLevelService.partialUpdate(applicantLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applicant-levels} : get all the applicantLevels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantLevels in body.
     */
    @GetMapping("/applicant-levels")
    public ResponseEntity<List<ApplicantLevelDTO>> getAllApplicantLevels(
        ApplicantLevelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicantLevels by criteria: {}", criteria);
        Page<ApplicantLevelDTO> page = applicantLevelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-levels/count} : count all the applicantLevels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-levels/count")
    public ResponseEntity<Long> countApplicantLevels(ApplicantLevelCriteria criteria) {
        log.debug("REST request to count ApplicantLevels by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantLevelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-levels/:id} : get the "id" applicantLevel.
     *
     * @param id the id of the applicantLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-levels/{id}")
    public ResponseEntity<ApplicantLevelDTO> getApplicantLevel(@PathVariable Long id) {
        log.debug("REST request to get ApplicantLevel : {}", id);
        Optional<ApplicantLevelDTO> applicantLevelDTO = applicantLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantLevelDTO);
    }

    /**
     * {@code DELETE  /applicant-levels/:id} : delete the "id" applicantLevel.
     *
     * @param id the id of the applicantLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-levels/{id}")
    public ResponseEntity<Void> deleteApplicantLevel(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantLevel : {}", id);
        applicantLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
