package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.IpInfo;
import com.reactit.kyc.repository.IpInfoRepository;
import com.reactit.kyc.service.IpInfoService;
import com.reactit.kyc.service.dto.IpInfoDTO;
import com.reactit.kyc.service.mapper.IpInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IpInfo}.
 */
@Service
@Transactional
public class IpInfoServiceImpl implements IpInfoService {

    private final Logger log = LoggerFactory.getLogger(IpInfoServiceImpl.class);

    private final IpInfoRepository ipInfoRepository;

    private final IpInfoMapper ipInfoMapper;

    public IpInfoServiceImpl(IpInfoRepository ipInfoRepository, IpInfoMapper ipInfoMapper) {
        this.ipInfoRepository = ipInfoRepository;
        this.ipInfoMapper = ipInfoMapper;
    }

    @Override
    public IpInfoDTO save(IpInfoDTO ipInfoDTO) {
        log.debug("Request to save IpInfo : {}", ipInfoDTO);
        IpInfo ipInfo = ipInfoMapper.toEntity(ipInfoDTO);
        ipInfo = ipInfoRepository.save(ipInfo);
        return ipInfoMapper.toDto(ipInfo);
    }

    @Override
    public Optional<IpInfoDTO> partialUpdate(IpInfoDTO ipInfoDTO) {
        log.debug("Request to partially update IpInfo : {}", ipInfoDTO);

        return ipInfoRepository
            .findById(ipInfoDTO.getId())
            .map(existingIpInfo -> {
                ipInfoMapper.partialUpdate(existingIpInfo, ipInfoDTO);

                return existingIpInfo;
            })
            .map(ipInfoRepository::save)
            .map(ipInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IpInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IpInfos");
        return ipInfoRepository.findAll(pageable).map(ipInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IpInfoDTO> findOne(Long id) {
        log.debug("Request to get IpInfo : {}", id);
        return ipInfoRepository.findById(id).map(ipInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IpInfo : {}", id);
        ipInfoRepository.deleteById(id);
    }
}
