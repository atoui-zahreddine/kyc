package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.ApplicantAddresseRepository;
import com.reactit.kyc.service.ApplicantAddresseQueryService;
import com.reactit.kyc.service.ApplicantAddresseService;
import com.reactit.kyc.service.criteria.ApplicantAddresseCriteria;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.ApplicantAddresse}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantAddresseResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddresseResource.class);

    private static final String ENTITY_NAME = "applicantAddresse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantAddresseService applicantAddresseService;

    private final ApplicantAddresseRepository applicantAddresseRepository;

    private final ApplicantAddresseQueryService applicantAddresseQueryService;

    public ApplicantAddresseResource(
        ApplicantAddresseService applicantAddresseService,
        ApplicantAddresseRepository applicantAddresseRepository,
        ApplicantAddresseQueryService applicantAddresseQueryService
    ) {
        this.applicantAddresseService = applicantAddresseService;
        this.applicantAddresseRepository = applicantAddresseRepository;
        this.applicantAddresseQueryService = applicantAddresseQueryService;
    }

    /**
     * {@code POST  /applicant-addresses} : Create a new applicantAddresse.
     *
     * @param applicantAddresseDTO the applicantAddresseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantAddresseDTO, or with status {@code 400 (Bad Request)} if the applicantAddresse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-addresses")
    public ResponseEntity<ApplicantAddresseDTO> createApplicantAddresse(@RequestBody ApplicantAddresseDTO applicantAddresseDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApplicantAddresse : {}", applicantAddresseDTO);
        if (applicantAddresseDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantAddresse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantAddresseDTO result = applicantAddresseService.save(applicantAddresseDTO);
        return ResponseEntity
            .created(new URI("/api/applicant-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-addresses/:id} : Updates an existing applicantAddresse.
     *
     * @param id the id of the applicantAddresseDTO to save.
     * @param applicantAddresseDTO the applicantAddresseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantAddresseDTO,
     * or with status {@code 400 (Bad Request)} if the applicantAddresseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantAddresseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-addresses/{id}")
    public ResponseEntity<ApplicantAddresseDTO> updateApplicantAddresse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantAddresseDTO applicantAddresseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicantAddresse : {}, {}", id, applicantAddresseDTO);
        if (applicantAddresseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantAddresseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantAddresseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicantAddresseDTO result = applicantAddresseService.save(applicantAddresseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantAddresseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicant-addresses/:id} : Partial updates given fields of an existing applicantAddresse, field will ignore if it is null
     *
     * @param id the id of the applicantAddresseDTO to save.
     * @param applicantAddresseDTO the applicantAddresseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantAddresseDTO,
     * or with status {@code 400 (Bad Request)} if the applicantAddresseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicantAddresseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicantAddresseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicant-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicantAddresseDTO> partialUpdateApplicantAddresse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantAddresseDTO applicantAddresseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicantAddresse partially : {}, {}", id, applicantAddresseDTO);
        if (applicantAddresseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantAddresseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantAddresseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicantAddresseDTO> result = applicantAddresseService.partialUpdate(applicantAddresseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantAddresseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applicant-addresses} : get all the applicantAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantAddresses in body.
     */
    @GetMapping("/applicant-addresses")
    public ResponseEntity<List<ApplicantAddresseDTO>> getAllApplicantAddresses(
        ApplicantAddresseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicantAddresses by criteria: {}", criteria);
        Page<ApplicantAddresseDTO> page = applicantAddresseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-addresses/count} : count all the applicantAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-addresses/count")
    public ResponseEntity<Long> countApplicantAddresses(ApplicantAddresseCriteria criteria) {
        log.debug("REST request to count ApplicantAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantAddresseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-addresses/:id} : get the "id" applicantAddresse.
     *
     * @param id the id of the applicantAddresseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantAddresseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-addresses/{id}")
    public ResponseEntity<ApplicantAddresseDTO> getApplicantAddresse(@PathVariable Long id) {
        log.debug("REST request to get ApplicantAddresse : {}", id);
        Optional<ApplicantAddresseDTO> applicantAddresseDTO = applicantAddresseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantAddresseDTO);
    }

    /**
     * {@code DELETE  /applicant-addresses/:id} : delete the "id" applicantAddresse.
     *
     * @param id the id of the applicantAddresseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-addresses/{id}")
    public ResponseEntity<Void> deleteApplicantAddresse(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantAddresse : {}", id);
        applicantAddresseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
