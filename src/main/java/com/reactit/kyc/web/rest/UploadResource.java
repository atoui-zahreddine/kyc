package com.reactit.kyc.web.rest;

import com.reactit.kyc.service.MinioService;
import com.reactit.kyc.service.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class UploadResource {

    @Autowired
    private MinioService minioService;

    @PostMapping
    public FileDTO uploadFile(@ModelAttribute FileDTO fileDTO) throws Exception {
        return minioService.uploadFile(fileDTO);
    }
}
