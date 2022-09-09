package com.reactit.kyc.service;

import com.reactit.kyc.service.dto.FileDTO;
import io.minio.*;
import io.minio.messages.Item;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
            throw new Exception("Happened error when get list objects from minio: ");
        }

        return objects;
    }

    public InputStream getObject(String filename) throws Exception {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build());
        } catch (Exception e) {
            throw new Exception("Happened error when get list objects from minio: ");
        }

        return stream;
    }

    public FileDTO uploadFile(FileDTO request) throws Exception {
        try {
            minioClient.putObject(
                PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(request.getFile().getOriginalFilename())
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build()
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        return new FileDTO(
            request.getFile().getOriginalFilename(),
            request.getFile().getSize(),
            getPreSignedUrl(request.getFile().getOriginalFilename())
        );
    }

    private String getPreSignedUrl(String filename) {
        return "/".concat(filename);
    }
}
