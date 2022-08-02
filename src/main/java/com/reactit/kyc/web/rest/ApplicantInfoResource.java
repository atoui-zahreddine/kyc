package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.ApplicantInfoRepository;
import com.reactit.kyc.service.ApplicantInfoQueryService;
import com.reactit.kyc.service.ApplicantInfoService;
import com.reactit.kyc.service.criteria.ApplicantInfoCriteria;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.ApplicantInfo}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantInfoResource.class);

    private static final String ENTITY_NAME = "applicantInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantInfoService applicantInfoService;

    private final ApplicantInfoRepository applicantInfoRepository;

    private final ApplicantInfoQueryService applicantInfoQueryService;

    public ApplicantInfoResource(
        ApplicantInfoService applicantInfoService,
        ApplicantInfoRepository applicantInfoRepository,
        ApplicantInfoQueryService applicantInfoQueryService
    ) {
        this.applicantInfoService = applicantInfoService;
        this.applicantInfoRepository = applicantInfoRepository;
        this.applicantInfoQueryService = applicantInfoQueryService;
    }

    /**
     * {@code POST  /applicant-infos} : Create a new applicantInfo.
     *
     * @param applicantInfoDTO the applicantInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantInfoDTO, or with status {@code 400 (Bad Request)} if the applicantInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-infos")
    public ResponseEntity<ApplicantInfoDTO> createApplicantInfo(@RequestBody ApplicantInfoDTO applicantInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicantInfo : {}", applicantInfoDTO);
        if (applicantInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantInfoDTO result = applicantInfoService.save(applicantInfoDTO);
        return ResponseEntity
            .created(new URI("/api/applicant-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-infos/:id} : Updates an existing applicantInfo.
     *
     * @param id the id of the applicantInfoDTO to save.
     * @param applicantInfoDTO the applicantInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantInfoDTO,
     * or with status {@code 400 (Bad Request)} if the applicantInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-infos/{id}")
    public ResponseEntity<ApplicantInfoDTO> updateApplicantInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantInfoDTO applicantInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicantInfo : {}, {}", id, applicantInfoDTO);
        if (applicantInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicantInfoDTO result = applicantInfoService.save(applicantInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicant-infos/:id} : Partial updates given fields of an existing applicantInfo, field will ignore if it is null
     *
     * @param id the id of the applicantInfoDTO to save.
     * @param applicantInfoDTO the applicantInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantInfoDTO,
     * or with status {@code 400 (Bad Request)} if the applicantInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicantInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicantInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicant-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicantInfoDTO> partialUpdateApplicantInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicantInfoDTO applicantInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicantInfo partially : {}, {}", id, applicantInfoDTO);
        if (applicantInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicantInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicantInfoDTO> result = applicantInfoService.partialUpdate(applicantInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /applicant-infos} : get all the applicantInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantInfos in body.
     */
    @GetMapping("/applicant-infos")
    public ResponseEntity<List<ApplicantInfoDTO>> getAllApplicantInfos(
        ApplicantInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicantInfos by criteria: {}", criteria);
        Page<ApplicantInfoDTO> page = applicantInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-infos/count} : count all the applicantInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-infos/count")
    public ResponseEntity<Long> countApplicantInfos(ApplicantInfoCriteria criteria) {
        log.debug("REST request to count ApplicantInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-infos/:id} : get the "id" applicantInfo.
     *
     * @param id the id of the applicantInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-infos/{id}")
    public ResponseEntity<ApplicantInfoDTO> getApplicantInfo(@PathVariable Long id) {
        log.debug("REST request to get ApplicantInfo : {}", id);
        Optional<ApplicantInfoDTO> applicantInfoDTO = applicantInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantInfoDTO);
    }

    /**
     * {@code DELETE  /applicant-infos/:id} : delete the "id" applicantInfo.
     *
     * @param id the id of the applicantInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-infos/{id}")
    public ResponseEntity<Void> deleteApplicantInfo(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantInfo : {}", id);
        applicantInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
