package com.reactit.kyc.web.rest;

import com.reactit.kyc.repository.UserAgentInfoRepository;
import com.reactit.kyc.service.UserAgentInfoQueryService;
import com.reactit.kyc.service.UserAgentInfoService;
import com.reactit.kyc.service.criteria.UserAgentInfoCriteria;
import com.reactit.kyc.service.dto.UserAgentInfoDTO;
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
 * REST controller for managing {@link com.reactit.kyc.domain.UserAgentInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserAgentInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserAgentInfoResource.class);

    private static final String ENTITY_NAME = "userAgentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAgentInfoService userAgentInfoService;

    private final UserAgentInfoRepository userAgentInfoRepository;

    private final UserAgentInfoQueryService userAgentInfoQueryService;

    public UserAgentInfoResource(
        UserAgentInfoService userAgentInfoService,
        UserAgentInfoRepository userAgentInfoRepository,
        UserAgentInfoQueryService userAgentInfoQueryService
    ) {
        this.userAgentInfoService = userAgentInfoService;
        this.userAgentInfoRepository = userAgentInfoRepository;
        this.userAgentInfoQueryService = userAgentInfoQueryService;
    }

    /**
     * {@code POST  /user-agent-infos} : Create a new userAgentInfo.
     *
     * @param userAgentInfoDTO the userAgentInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAgentInfoDTO, or with status {@code 400 (Bad Request)} if the userAgentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-agent-infos")
    public ResponseEntity<UserAgentInfoDTO> createUserAgentInfo(@RequestBody UserAgentInfoDTO userAgentInfoDTO) throws URISyntaxException {
        log.debug("REST request to save UserAgentInfo : {}", userAgentInfoDTO);
        if (userAgentInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAgentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAgentInfoDTO result = userAgentInfoService.save(userAgentInfoDTO);
        return ResponseEntity
            .created(new URI("/api/user-agent-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-agent-infos/:id} : Updates an existing userAgentInfo.
     *
     * @param id the id of the userAgentInfoDTO to save.
     * @param userAgentInfoDTO the userAgentInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAgentInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userAgentInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAgentInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-agent-infos/{id}")
    public ResponseEntity<UserAgentInfoDTO> updateUserAgentInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAgentInfoDTO userAgentInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAgentInfo : {}, {}", id, userAgentInfoDTO);
        if (userAgentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAgentInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAgentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAgentInfoDTO result = userAgentInfoService.save(userAgentInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAgentInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-agent-infos/:id} : Partial updates given fields of an existing userAgentInfo, field will ignore if it is null
     *
     * @param id the id of the userAgentInfoDTO to save.
     * @param userAgentInfoDTO the userAgentInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAgentInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userAgentInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAgentInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAgentInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-agent-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAgentInfoDTO> partialUpdateUserAgentInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAgentInfoDTO userAgentInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAgentInfo partially : {}, {}", id, userAgentInfoDTO);
        if (userAgentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAgentInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAgentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAgentInfoDTO> result = userAgentInfoService.partialUpdate(userAgentInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAgentInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-agent-infos} : get all the userAgentInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAgentInfos in body.
     */
    @GetMapping("/user-agent-infos")
    public ResponseEntity<List<UserAgentInfoDTO>> getAllUserAgentInfos(
        UserAgentInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserAgentInfos by criteria: {}", criteria);
        Page<UserAgentInfoDTO> page = userAgentInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-agent-infos/count} : count all the userAgentInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-agent-infos/count")
    public ResponseEntity<Long> countUserAgentInfos(UserAgentInfoCriteria criteria) {
        log.debug("REST request to count UserAgentInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAgentInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-agent-infos/:id} : get the "id" userAgentInfo.
     *
     * @param id the id of the userAgentInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAgentInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-agent-infos/{id}")
    public ResponseEntity<UserAgentInfoDTO> getUserAgentInfo(@PathVariable Long id) {
        log.debug("REST request to get UserAgentInfo : {}", id);
        Optional<UserAgentInfoDTO> userAgentInfoDTO = userAgentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAgentInfoDTO);
    }

    /**
     * {@code DELETE  /user-agent-infos/:id} : delete the "id" userAgentInfo.
     *
     * @param id the id of the userAgentInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-agent-infos/{id}")
    public ResponseEntity<Void> deleteUserAgentInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserAgentInfo : {}", id);
        userAgentInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
