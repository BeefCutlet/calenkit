package com.effourt.calenkit.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUpload {

    private static final String OBJECT_PATH = "profile-image";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        //고유 ID값을 부여해서 이미지 이름 중복되지 않게 처리.
        String filename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        String filePath = OBJECT_PATH + "/" + filename;

        //로컬에 파일 저장
        String localFilePath = System.getProperty("user.home") + "/" + OBJECT_PATH;
        File dir = new File(localFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(localFilePath + "/" + filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        //AWS S3에 파일 저장
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, filePath, file);

        amazonS3Client.putObject(objectRequest);

        return amazonS3Client.getUrl(bucket, filePath).toString();
    }

    public String getOriginalFilename(String filename) {
        return filename.substring(filename.indexOf("_") + 1);
    }
}