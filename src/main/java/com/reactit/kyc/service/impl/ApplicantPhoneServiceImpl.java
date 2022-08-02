package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.ApplicantPhone;
import com.reactit.kyc.repository.ApplicantPhoneRepository;
import com.reactit.kyc.service.ApplicantPhoneService;
import com.reactit.kyc.service.dto.ApplicantPhoneDTO;
import com.reactit.kyc.service.mapper.ApplicantPhoneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicantPhone}.
 */
@Service
@Transactional
public class ApplicantPhoneServiceImpl implements ApplicantPhoneService {

    private final Logger log = LoggerFactory.getLogger(ApplicantPhoneServiceImpl.class);

    private final ApplicantPhoneRepository applicantPhoneRepository;

    private final ApplicantPhoneMapper applicantPhoneMapper;

    public ApplicantPhoneServiceImpl(ApplicantPhoneRepository applicantPhoneRepository, ApplicantPhoneMapper applicantPhoneMapper) {
        this.applicantPhoneRepository = applicantPhoneRepository;
        this.applicantPhoneMapper = applicantPhoneMapper;
    }

    @Override
    public ApplicantPhoneDTO save(ApplicantPhoneDTO applicantPhoneDTO) {
        log.debug("Request to save ApplicantPhone : {}", applicantPhoneDTO);
        ApplicantPhone applicantPhone = applicantPhoneMapper.toEntity(applicantPhoneDTO);
        applicantPhone = applicantPhoneRepository.save(applicantPhone);
        return applicantPhoneMapper.toDto(applicantPhone);
    }

    @Override
    public Optional<ApplicantPhoneDTO> partialUpdate(ApplicantPhoneDTO applicantPhoneDTO) {
        log.debug("Request to partially update ApplicantPhone : {}", applicantPhoneDTO);

        return applicantPhoneRepository
            .findById(applicantPhoneDTO.getId())
            .map(existingApplicantPhone -> {
                applicantPhoneMapper.partialUpdate(existingApplicantPhone, applicantPhoneDTO);

                return existingApplicantPhone;
            })
            .map(applicantPhoneRepository::save)
            .map(applicantPhoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantPhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantPhones");
        return applicantPhoneRepository.findAll(pageable).map(applicantPhoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantPhoneDTO> findOne(Long id) {
        log.debug("Request to get ApplicantPhone : {}", id);
        return applicantPhoneRepository.findById(id).map(applicantPhoneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicantPhone : {}", id);
        applicantPhoneRepository.deleteById(id);
    }
}
