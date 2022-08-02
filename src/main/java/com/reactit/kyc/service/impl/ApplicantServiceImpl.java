package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.Applicant;
import com.reactit.kyc.repository.ApplicantRepository;
import com.reactit.kyc.service.ApplicantService;
import com.reactit.kyc.service.dto.ApplicantDTO;
import com.reactit.kyc.service.mapper.ApplicantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Applicant}.
 */
@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {

    private final Logger log = LoggerFactory.getLogger(ApplicantServiceImpl.class);

    private final ApplicantRepository applicantRepository;

    private final ApplicantMapper applicantMapper;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public ApplicantDTO save(ApplicantDTO applicantDTO) {
        log.debug("Request to save Applicant : {}", applicantDTO);
        Applicant applicant = applicantMapper.toEntity(applicantDTO);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.toDto(applicant);
    }

    @Override
    public Optional<ApplicantDTO> partialUpdate(ApplicantDTO applicantDTO) {
        log.debug("Request to partially update Applicant : {}", applicantDTO);

        return applicantRepository
            .findById(applicantDTO.getId())
            .map(existingApplicant -> {
                applicantMapper.partialUpdate(existingApplicant, applicantDTO);

                return existingApplicant;
            })
            .map(applicantRepository::save)
            .map(applicantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll(pageable).map(applicantMapper::toDto);
    }

    /**
     *  Get all the applicants where ApplicantInfo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantDTO> findAllWhereApplicantInfoIsNull() {
        log.debug("Request to get all applicants where ApplicantInfo is null");
        return StreamSupport
            .stream(applicantRepository.findAll().spliterator(), false)
            .filter(applicant -> applicant.getApplicantInfo() == null)
            .map(applicantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the applicants where IpInfo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantDTO> findAllWhereIpInfoIsNull() {
        log.debug("Request to get all applicants where IpInfo is null");
        return StreamSupport
            .stream(applicantRepository.findAll().spliterator(), false)
            .filter(applicant -> applicant.getIpInfo() == null)
            .map(applicantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the applicants where UserAgentInfo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantDTO> findAllWhereUserAgentInfoIsNull() {
        log.debug("Request to get all applicants where UserAgentInfo is null");
        return StreamSupport
            .stream(applicantRepository.findAll().spliterator(), false)
            .filter(applicant -> applicant.getUserAgentInfo() == null)
            .map(applicantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantDTO> findOne(Long id) {
        log.debug("Request to get Applicant : {}", id);
        return applicantRepository.findById(id).map(applicantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
    }
}
