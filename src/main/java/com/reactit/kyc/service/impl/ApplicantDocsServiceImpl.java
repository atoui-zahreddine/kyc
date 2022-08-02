package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.ApplicantDocs;
import com.reactit.kyc.repository.ApplicantDocsRepository;
import com.reactit.kyc.service.ApplicantDocsService;
import com.reactit.kyc.service.dto.ApplicantDocsDTO;
import com.reactit.kyc.service.mapper.ApplicantDocsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicantDocs}.
 */
@Service
@Transactional
public class ApplicantDocsServiceImpl implements ApplicantDocsService {

    private final Logger log = LoggerFactory.getLogger(ApplicantDocsServiceImpl.class);

    private final ApplicantDocsRepository applicantDocsRepository;

    private final ApplicantDocsMapper applicantDocsMapper;

    public ApplicantDocsServiceImpl(ApplicantDocsRepository applicantDocsRepository, ApplicantDocsMapper applicantDocsMapper) {
        this.applicantDocsRepository = applicantDocsRepository;
        this.applicantDocsMapper = applicantDocsMapper;
    }

    @Override
    public ApplicantDocsDTO save(ApplicantDocsDTO applicantDocsDTO) {
        log.debug("Request to save ApplicantDocs : {}", applicantDocsDTO);
        ApplicantDocs applicantDocs = applicantDocsMapper.toEntity(applicantDocsDTO);
        applicantDocs = applicantDocsRepository.save(applicantDocs);
        return applicantDocsMapper.toDto(applicantDocs);
    }

    @Override
    public Optional<ApplicantDocsDTO> partialUpdate(ApplicantDocsDTO applicantDocsDTO) {
        log.debug("Request to partially update ApplicantDocs : {}", applicantDocsDTO);

        return applicantDocsRepository
            .findById(applicantDocsDTO.getId())
            .map(existingApplicantDocs -> {
                applicantDocsMapper.partialUpdate(existingApplicantDocs, applicantDocsDTO);

                return existingApplicantDocs;
            })
            .map(applicantDocsRepository::save)
            .map(applicantDocsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantDocsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantDocs");
        return applicantDocsRepository.findAll(pageable).map(applicantDocsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantDocsDTO> findOne(Long id) {
        log.debug("Request to get ApplicantDocs : {}", id);
        return applicantDocsRepository.findById(id).map(applicantDocsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicantDocs : {}", id);
        applicantDocsRepository.deleteById(id);
    }
}
