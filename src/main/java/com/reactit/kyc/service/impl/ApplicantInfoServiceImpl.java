package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.ApplicantInfo;
import com.reactit.kyc.repository.ApplicantInfoRepository;
import com.reactit.kyc.service.ApplicantInfoService;
import com.reactit.kyc.service.dto.ApplicantInfoDTO;
import com.reactit.kyc.service.mapper.ApplicantInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicantInfo}.
 */
@Service
@Transactional
public class ApplicantInfoServiceImpl implements ApplicantInfoService {

    private final Logger log = LoggerFactory.getLogger(ApplicantInfoServiceImpl.class);

    private final ApplicantInfoRepository applicantInfoRepository;

    private final ApplicantInfoMapper applicantInfoMapper;

    public ApplicantInfoServiceImpl(ApplicantInfoRepository applicantInfoRepository, ApplicantInfoMapper applicantInfoMapper) {
        this.applicantInfoRepository = applicantInfoRepository;
        this.applicantInfoMapper = applicantInfoMapper;
    }

    @Override
    public ApplicantInfoDTO save(ApplicantInfoDTO applicantInfoDTO) {
        log.debug("Request to save ApplicantInfo : {}", applicantInfoDTO);
        ApplicantInfo applicantInfo = applicantInfoMapper.toEntity(applicantInfoDTO);
        applicantInfo = applicantInfoRepository.save(applicantInfo);
        return applicantInfoMapper.toDto(applicantInfo);
    }

    @Override
    public Optional<ApplicantInfoDTO> partialUpdate(ApplicantInfoDTO applicantInfoDTO) {
        log.debug("Request to partially update ApplicantInfo : {}", applicantInfoDTO);

        return applicantInfoRepository
            .findById(applicantInfoDTO.getId())
            .map(existingApplicantInfo -> {
                applicantInfoMapper.partialUpdate(existingApplicantInfo, applicantInfoDTO);

                return existingApplicantInfo;
            })
            .map(applicantInfoRepository::save)
            .map(applicantInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantInfos");
        return applicantInfoRepository.findAll(pageable).map(applicantInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantInfoDTO> findOne(Long id) {
        log.debug("Request to get ApplicantInfo : {}", id);
        return applicantInfoRepository.findById(id).map(applicantInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicantInfo : {}", id);
        applicantInfoRepository.deleteById(id);
    }
}
