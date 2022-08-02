package com.reactit.kyc.service.impl;

import com.reactit.kyc.domain.UserAgentInfo;
import com.reactit.kyc.repository.UserAgentInfoRepository;
import com.reactit.kyc.service.UserAgentInfoService;
import com.reactit.kyc.service.dto.UserAgentInfoDTO;
import com.reactit.kyc.service.mapper.UserAgentInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserAgentInfo}.
 */
@Service
@Transactional
public class UserAgentInfoServiceImpl implements UserAgentInfoService {

    private final Logger log = LoggerFactory.getLogger(UserAgentInfoServiceImpl.class);

    private final UserAgentInfoRepository userAgentInfoRepository;

    private final UserAgentInfoMapper userAgentInfoMapper;

    public UserAgentInfoServiceImpl(UserAgentInfoRepository userAgentInfoRepository, UserAgentInfoMapper userAgentInfoMapper) {
        this.userAgentInfoRepository = userAgentInfoRepository;
        this.userAgentInfoMapper = userAgentInfoMapper;
    }

    @Override
    public UserAgentInfoDTO save(UserAgentInfoDTO userAgentInfoDTO) {
        log.debug("Request to save UserAgentInfo : {}", userAgentInfoDTO);
        UserAgentInfo userAgentInfo = userAgentInfoMapper.toEntity(userAgentInfoDTO);
        userAgentInfo = userAgentInfoRepository.save(userAgentInfo);
        return userAgentInfoMapper.toDto(userAgentInfo);
    }

    @Override
    public Optional<UserAgentInfoDTO> partialUpdate(UserAgentInfoDTO userAgentInfoDTO) {
        log.debug("Request to partially update UserAgentInfo : {}", userAgentInfoDTO);

        return userAgentInfoRepository
            .findById(userAgentInfoDTO.getId())
            .map(existingUserAgentInfo -> {
                userAgentInfoMapper.partialUpdate(existingUserAgentInfo, userAgentInfoDTO);

                return existingUserAgentInfo;
            })
            .map(userAgentInfoRepository::save)
            .map(userAgentInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAgentInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAgentInfos");
        return userAgentInfoRepository.findAll(pageable).map(userAgentInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAgentInfoDTO> findOne(Long id) {
        log.debug("Request to get UserAgentInfo : {}", id);
        return userAgentInfoRepository.findById(id).map(userAgentInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAgentInfo : {}", id);
        userAgentInfoRepository.deleteById(id);
    }
}
