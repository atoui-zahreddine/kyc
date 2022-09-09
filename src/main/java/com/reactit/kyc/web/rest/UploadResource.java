package com.reactit.kyc.web.rest;

import com.reactit.kyc.service.MinioService;
import com.reactit.kyc.service.dto.FileDTO;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UploadResource {

    @Autowired
    private MinioService minioService;

    @PostMapping("/files")
    public FileDTO uploadFile(@ModelAttribute FileDTO fileDTO) throws Exception {
        return minioService.uploadFile(fileDTO);
    }

    @GetMapping(value = "/files/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) throws IOException, Exception {
        return minioService.getObject(fileName);
    }
}
