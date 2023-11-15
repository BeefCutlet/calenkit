package com.effourt.calenkit.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUpload {

    private static final String OBJECT_PATH = "profile-image";
    private static final String PUBLIC_URL = "https://storage.googleapis.com";

    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    /**
     * 이미지 업로드 메서드
     * Amazon S3의 bucket에 파일 저장
     * 파일 이름 앞에는 UUID 8자리를 붙이고, 언더바로 구분
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        //고유 ID값을 부여해서 이미지 이름 중복되지 않게 처리.
        String filename = UUID.randomUUID().toString().substring(0, 8) + "_"
                + multipartFile.getOriginalFilename();
        String filePath = OBJECT_PATH + "/" + filename;
        String ext = multipartFile.getContentType();
        log.info("filename=" + filename);
        log.info("filePath=" + filePath);

        //GCS에 파일 저장
        Blob blob = storage.create(
                BlobInfo.newBuilder(bucketName, filePath)
                        .setContentType(ext)
                        .build(),
                multipartFile.getBytes()
        );

        String fullUrlPath = PUBLIC_URL + "/" + blob.getBucket() + "/" + blob.getName();
        log.info("saved image url={}", fullUrlPath);

        return fullUrlPath;
    }
}