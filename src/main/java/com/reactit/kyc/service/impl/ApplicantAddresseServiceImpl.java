package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.ApplicantAddresse;
import com.reactit.kyc.repository.ApplicantAddresseRepository;
import com.reactit.kyc.service.ApplicantAddresseService;
import com.reactit.kyc.service.dto.ApplicantAddresseDTO;
import com.reactit.kyc.service.mapper.ApplicantAddresseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicantAddresse}.
 */
@Service
@Transactional
public class ApplicantAddresseServiceImpl implements ApplicantAddresseService {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddresseServiceImpl.class);

    private final ApplicantAddresseRepository applicantAddresseRepository;

    private final ApplicantAddresseMapper applicantAddresseMapper;

    public ApplicantAddresseServiceImpl(
        ApplicantAddresseRepository applicantAddresseRepository,
        ApplicantAddresseMapper applicantAddresseMapper
    ) {
        this.applicantAddresseRepository = applicantAddresseRepository;
        this.applicantAddresseMapper = applicantAddresseMapper;
    }

    @Override
    public ApplicantAddresseDTO save(ApplicantAddresseDTO applicantAddresseDTO) {
        log.debug("Request to save ApplicantAddresse : {}", applicantAddresseDTO);
        ApplicantAddresse applicantAddresse = applicantAddresseMapper.toEntity(applicantAddresseDTO);
        applicantAddresse = applicantAddresseRepository.save(applicantAddresse);
        return applicantAddresseMapper.toDto(applicantAddresse);
    }

    @Override
    public Optional<ApplicantAddresseDTO> partialUpdate(ApplicantAddresseDTO applicantAddresseDTO) {
        log.debug("Request to partially update ApplicantAddresse : {}", applicantAddresseDTO);

        return applicantAddresseRepository
            .findById(applicantAddresseDTO.getId())
            .map(existingApplicantAddresse -> {
                applicantAddresseMapper.partialUpdate(existingApplicantAddresse, applicantAddresseDTO);

                return existingApplicantAddresse;
            })
            .map(applicantAddresseRepository::save)
            .map(applicantAddresseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantAddresseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantAddresses");
        return applicantAddresseRepository.findAll(pageable).map(applicantAddresseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantAddresseDTO> findOne(Long id) {
        log.debug("Request to get ApplicantAddresse : {}", id);
        return applicantAddresseRepository.findById(id).map(applicantAddresseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicantAddresse : {}", id);
        applicantAddresseRepository.deleteById(id);
    }
}
