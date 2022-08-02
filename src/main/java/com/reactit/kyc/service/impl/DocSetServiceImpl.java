package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.DocSet;
import com.reactit.kyc.repository.DocSetRepository;
import com.reactit.kyc.service.DocSetService;
import com.reactit.kyc.service.dto.DocSetDTO;
import com.reactit.kyc.service.mapper.DocSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocSet}.
 */
@Service
@Transactional
public class DocSetServiceImpl implements DocSetService {

    private final Logger log = LoggerFactory.getLogger(DocSetServiceImpl.class);

    private final DocSetRepository docSetRepository;

    private final DocSetMapper docSetMapper;

    public DocSetServiceImpl(DocSetRepository docSetRepository, DocSetMapper docSetMapper) {
        this.docSetRepository = docSetRepository;
        this.docSetMapper = docSetMapper;
    }

    @Override
    public DocSetDTO save(DocSetDTO docSetDTO) {
        log.debug("Request to save DocSet : {}", docSetDTO);
        DocSet docSet = docSetMapper.toEntity(docSetDTO);
        docSet = docSetRepository.save(docSet);
        return docSetMapper.toDto(docSet);
    }

    @Override
    public Optional<DocSetDTO> partialUpdate(DocSetDTO docSetDTO) {
        log.debug("Request to partially update DocSet : {}", docSetDTO);

        return docSetRepository
            .findById(docSetDTO.getId())
            .map(existingDocSet -> {
                docSetMapper.partialUpdate(existingDocSet, docSetDTO);

                return existingDocSet;
            })
            .map(docSetRepository::save)
            .map(docSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocSets");
        return docSetRepository.findAll(pageable).map(docSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocSetDTO> findOne(Long id) {
        log.debug("Request to get DocSet : {}", id);
        return docSetRepository.findById(id).map(docSetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocSet : {}", id);
        docSetRepository.deleteById(id);
    }
}
