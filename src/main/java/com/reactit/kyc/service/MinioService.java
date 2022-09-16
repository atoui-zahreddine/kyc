package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.FileDTO;
import io.minio.*;
import io.minio.messages.Item;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public List<FileDTO> getListObjects() throws Exception {
        List<FileDTO> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
            for (Result<Item> item : result) {
                objects.add(new FileDTO(item.get().objectName(), item.get().size(), getPreSignedUrl(item.get().objectName())));
            }
        } catch (Exception e) {
            throw new Exception("Happened error when get list objects from minio: " + e.getMessage());
        }

        return objects;
    }

    public ResponseEntity<byte[]> getObject(String filename) throws Exception {
        GetObjectResponse objectResponse;
        try {
            objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build());
        } catch (Exception e) {
            throw new Exception("Happened error when get list objects from minio: " + e.getMessage());
        }
        var contentType = objectResponse.headers().get("Content-Type");
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        InputStream stream = objectResponse;
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(IOUtils.toByteArray(stream));
    }

    public FileDTO uploadFile(FileDTO request) throws Exception {
        try {
            var contentType = request.getFile().getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            minioClient.putObject(
                PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(String.join("/", request.getPrePath(), request.getFile().getOriginalFilename()))
                    .contentType(contentType)
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build()
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        return new FileDTO(
            request.getFile().getOriginalFilename(),
            request.getFile().getSize(),
            getPreSignedUrl(String.join("/", request.getPrePath(), request.getFile().getOriginalFilename()))
        );
    }

    private String getPreSignedUrl(String filename) {
        return "/".concat(filename);
    }
}
