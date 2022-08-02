package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.Step;
import com.reactit.kyc.repository.StepRepository;
import com.reactit.kyc.service.StepService;
import com.reactit.kyc.service.dto.StepDTO;
import com.reactit.kyc.service.mapper.StepMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Step}.
 */
@Service
@Transactional
public class StepServiceImpl implements StepService {

    private final Logger log = LoggerFactory.getLogger(StepServiceImpl.class);

    private final StepRepository stepRepository;

    private final StepMapper stepMapper;

    public StepServiceImpl(StepRepository stepRepository, StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
    }

    @Override
    public StepDTO save(StepDTO stepDTO) {
        log.debug("Request to save Step : {}", stepDTO);
        Step step = stepMapper.toEntity(stepDTO);
        step = stepRepository.save(step);
        return stepMapper.toDto(step);
    }

    @Override
    public Optional<StepDTO> partialUpdate(StepDTO stepDTO) {
        log.debug("Request to partially update Step : {}", stepDTO);

        return stepRepository
            .findById(stepDTO.getId())
            .map(existingStep -> {
                stepMapper.partialUpdate(existingStep, stepDTO);

                return existingStep;
            })
            .map(stepRepository::save)
            .map(stepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Steps");
        return stepRepository.findAll(pageable).map(stepMapper::toDto);
    }

    public Page<StepDTO> findAllWithEagerRelationships(Pageable pageable) {
        return stepRepository.findAllWithEagerRelationships(pageable).map(stepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepDTO> findOne(Long id) {
        log.debug("Request to get Step : {}", id);
        return stepRepository.findOneWithEagerRelationships(id).map(stepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Step : {}", id);
        stepRepository.deleteById(id);
    }
}
