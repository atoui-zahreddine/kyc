package com.reactit.kyc.web.rest;

import com.reactit.kyc.service.MinioService;
import com.reactit.kyc.service.dto.FileDTO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("/api")
public class UploadResource {

    @Autowired
    private MinioService minioService;

    @PostMapping("/files")
    public FileDTO uploadFile(@ModelAttribute FileDTO fileDTO) throws Exception {
        return minioService.uploadFile(fileDTO);
    }

    @GetMapping(value = "/files/**")
    public ResponseEntity<?> getFile(HttpServletRequest request) throws IOException, Exception {
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        String fileName = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        return minioService.getObject(fileName);
    }
}
