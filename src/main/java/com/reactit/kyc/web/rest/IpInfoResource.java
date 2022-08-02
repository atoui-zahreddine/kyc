package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.IpInfoRepository;
import com.reactit.kyc.service.IpInfoQueryService;
import com.reactit.kyc.service.IpInfoService;
import com.reactit.kyc.service.criteria.IpInfoCriteria;
import com.reactit.kyc.service.dto.IpInfoDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.IpInfo}.
 */
@RestController
@RequestMapping("/api")
public class IpInfoResource {

    private final Logger log = LoggerFactory.getLogger(IpInfoResource.class);

    private static final String ENTITY_NAME = "ipInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IpInfoService ipInfoService;

    private final IpInfoRepository ipInfoRepository;

    private final IpInfoQueryService ipInfoQueryService;

    public IpInfoResource(IpInfoService ipInfoService, IpInfoRepository ipInfoRepository, IpInfoQueryService ipInfoQueryService) {
        this.ipInfoService = ipInfoService;
        this.ipInfoRepository = ipInfoRepository;
        this.ipInfoQueryService = ipInfoQueryService;
    }

    /**
     * {@code POST  /ip-infos} : Create a new ipInfo.
     *
     * @param ipInfoDTO the ipInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ipInfoDTO, or with status {@code 400 (Bad Request)} if the ipInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ip-infos")
    public ResponseEntity<IpInfoDTO> createIpInfo(@RequestBody IpInfoDTO ipInfoDTO) throws URISyntaxException {
        log.debug("REST request to save IpInfo : {}", ipInfoDTO);
        if (ipInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ipInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IpInfoDTO result = ipInfoService.save(ipInfoDTO);
        return ResponseEntity
            .created(new URI("/api/ip-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ip-infos/:id} : Updates an existing ipInfo.
     *
     * @param id the id of the ipInfoDTO to save.
     * @param ipInfoDTO the ipInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ipInfoDTO,
     * or with status {@code 400 (Bad Request)} if the ipInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ipInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ip-infos/{id}")
    public ResponseEntity<IpInfoDTO> updateIpInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IpInfoDTO ipInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IpInfo : {}, {}", id, ipInfoDTO);
        if (ipInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ipInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ipInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IpInfoDTO result = ipInfoService.save(ipInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ipInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ip-infos/:id} : Partial updates given fields of an existing ipInfo, field will ignore if it is null
     *
     * @param id the id of the ipInfoDTO to save.
     * @param ipInfoDTO the ipInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ipInfoDTO,
     * or with status {@code 400 (Bad Request)} if the ipInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ipInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ipInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ip-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IpInfoDTO> partialUpdateIpInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IpInfoDTO ipInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IpInfo partially : {}, {}", id, ipInfoDTO);
        if (ipInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ipInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ipInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IpInfoDTO> result = ipInfoService.partialUpdate(ipInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ipInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ip-infos} : get all the ipInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ipInfos in body.
     */
    @GetMapping("/ip-infos")
    public ResponseEntity<List<IpInfoDTO>> getAllIpInfos(
        IpInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IpInfos by criteria: {}", criteria);
        Page<IpInfoDTO> page = ipInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ip-infos/count} : count all the ipInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ip-infos/count")
    public ResponseEntity<Long> countIpInfos(IpInfoCriteria criteria) {
        log.debug("REST request to count IpInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ipInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ip-infos/:id} : get the "id" ipInfo.
     *
     * @param id the id of the ipInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ipInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ip-infos/{id}")
    public ResponseEntity<IpInfoDTO> getIpInfo(@PathVariable Long id) {
        log.debug("REST request to get IpInfo : {}", id);
        Optional<IpInfoDTO> ipInfoDTO = ipInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ipInfoDTO);
    }

    /**
     * {@code DELETE  /ip-infos/:id} : delete the "id" ipInfo.
     *
     * @param id the id of the ipInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ip-infos/{id}")
    public ResponseEntity<Void> deleteIpInfo(@PathVariable Long id) {
        log.debug("REST request to delete IpInfo : {}", id);
        ipInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
