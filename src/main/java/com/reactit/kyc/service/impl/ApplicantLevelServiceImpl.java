package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.ApplicantLevel;
import com.reactit.kyc.repository.ApplicantLevelRepository;
import com.reactit.kyc.service.ApplicantLevelService;
import com.reactit.kyc.service.dto.ApplicantLevelDTO;
import com.reactit.kyc.service.mapper.ApplicantLevelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicantLevel}.
 */
@Service
@Transactional
public class ApplicantLevelServiceImpl implements ApplicantLevelService {

    private final Logger log = LoggerFactory.getLogger(ApplicantLevelServiceImpl.class);

    private final ApplicantLevelRepository applicantLevelRepository;

    private final ApplicantLevelMapper applicantLevelMapper;

    public ApplicantLevelServiceImpl(ApplicantLevelRepository applicantLevelRepository, ApplicantLevelMapper applicantLevelMapper) {
        this.applicantLevelRepository = applicantLevelRepository;
        this.applicantLevelMapper = applicantLevelMapper;
    }

    @Override
    public ApplicantLevelDTO save(ApplicantLevelDTO applicantLevelDTO) {
        log.debug("Request to save ApplicantLevel : {}", applicantLevelDTO);
        ApplicantLevel applicantLevel = applicantLevelMapper.toEntity(applicantLevelDTO);
        applicantLevel = applicantLevelRepository.save(applicantLevel);
        return applicantLevelMapper.toDto(applicantLevel);
    }

    @Override
    public Optional<ApplicantLevelDTO> partialUpdate(ApplicantLevelDTO applicantLevelDTO) {
        log.debug("Request to partially update ApplicantLevel : {}", applicantLevelDTO);

        return applicantLevelRepository
            .findById(applicantLevelDTO.getId())
            .map(existingApplicantLevel -> {
                applicantLevelMapper.partialUpdate(existingApplicantLevel, applicantLevelDTO);

                return existingApplicantLevel;
            })
            .map(applicantLevelRepository::save)
            .map(applicantLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantLevels");
        return applicantLevelRepository.findAll(pageable).map(applicantLevelMapper::toDto);
    }

    public Page<ApplicantLevelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return applicantLevelRepository.findAllWithEagerRelationships(pageable).map(applicantLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantLevelDTO> findOne(Long id) {
        log.debug("Request to get ApplicantLevel : {}", id);
        return applicantLevelRepository.findOneWithEagerRelationships(id).map(applicantLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicantLevel : {}", id);
        applicantLevelRepository.deleteById(id);
    }
}
