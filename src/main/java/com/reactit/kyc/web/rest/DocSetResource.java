package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.DocSetRepository;
import com.reactit.kyc.service.DocSetQueryService;
import com.reactit.kyc.service.DocSetService;
import com.reactit.kyc.service.criteria.DocSetCriteria;
import com.reactit.kyc.service.dto.DocSetDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.DocSet}.
 */
@RestController
@RequestMapping("/api")
public class DocSetResource {

    private final Logger log = LoggerFactory.getLogger(DocSetResource.class);

    private static final String ENTITY_NAME = "docSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocSetService docSetService;

    private final DocSetRepository docSetRepository;

    private final DocSetQueryService docSetQueryService;

    public DocSetResource(DocSetService docSetService, DocSetRepository docSetRepository, DocSetQueryService docSetQueryService) {
        this.docSetService = docSetService;
        this.docSetRepository = docSetRepository;
        this.docSetQueryService = docSetQueryService;
    }

    /**
     * {@code POST  /doc-sets} : Create a new docSet.
     *
     * @param docSetDTO the docSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docSetDTO, or with status {@code 400 (Bad Request)} if the docSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doc-sets")
    public ResponseEntity<DocSetDTO> createDocSet(@RequestBody DocSetDTO docSetDTO) throws URISyntaxException {
        log.debug("REST request to save DocSet : {}", docSetDTO);
        if (docSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new docSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocSetDTO result = docSetService.save(docSetDTO);
        return ResponseEntity
            .created(new URI("/api/doc-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doc-sets/:id} : Updates an existing docSet.
     *
     * @param id the id of the docSetDTO to save.
     * @param docSetDTO the docSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docSetDTO,
     * or with status {@code 400 (Bad Request)} if the docSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doc-sets/{id}")
    public ResponseEntity<DocSetDTO> updateDocSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocSetDTO docSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocSet : {}, {}", id, docSetDTO);
        if (docSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocSetDTO result = docSetService.save(docSetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, docSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doc-sets/:id} : Partial updates given fields of an existing docSet, field will ignore if it is null
     *
     * @param id the id of the docSetDTO to save.
     * @param docSetDTO the docSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docSetDTO,
     * or with status {@code 400 (Bad Request)} if the docSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the docSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the docSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doc-sets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocSetDTO> partialUpdateDocSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocSetDTO docSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocSet partially : {}, {}", id, docSetDTO);
        if (docSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocSetDTO> result = docSetService.partialUpdate(docSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, docSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /doc-sets} : get all the docSets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docSets in body.
     */
    @GetMapping("/doc-sets")
    public ResponseEntity<List<DocSetDTO>> getAllDocSets(
        DocSetCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DocSets by criteria: {}", criteria);
        Page<DocSetDTO> page = docSetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doc-sets/count} : count all the docSets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/doc-sets/count")
    public ResponseEntity<Long> countDocSets(DocSetCriteria criteria) {
        log.debug("REST request to count DocSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(docSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /doc-sets/:id} : get the "id" docSet.
     *
     * @param id the id of the docSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doc-sets/{id}")
    public ResponseEntity<DocSetDTO> getDocSet(@PathVariable Long id) {
        log.debug("REST request to get DocSet : {}", id);
        Optional<DocSetDTO> docSetDTO = docSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docSetDTO);
    }

    /**
     * {@code DELETE  /doc-sets/:id} : delete the "id" docSet.
     *
     * @param id the id of the docSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doc-sets/{id}")
    public ResponseEntity<Void> deleteDocSet(@PathVariable Long id) {
        log.debug("REST request to delete DocSet : {}", id);
        docSetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
