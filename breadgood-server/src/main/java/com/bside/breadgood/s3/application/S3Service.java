package com.bside.breadgood.s3.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bside.breadgood.s3.application.dto.S3UploadResponseDto;
import com.bside.breadgood.s3.application.exception.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloud-front.domain-name}")
    public String cloudFrontDns;

    private final AmazonS3Client s3Client;

//    public static final String HEADER_FILE_NAME = "filename";

//    @PostConstruct
//    public void setS3Client() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//
//        s3Client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(this.region)
//                .build();
//    }

    public S3UploadResponseDto upload(MultipartFile[] files, String dirName) {

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            final String filePath = upload(file, dirName);
            filePaths.add(filePath);
        }

        return S3UploadResponseDto.builder()
                .filePaths(filePaths)
                .fileHost(cloudFrontDns)
                .build();
    }

    public String upload(MultipartFile file, String dirPath) {

        // 고유한 key 값을 갖기위해 현재 시간을 postfix 로 붙여줌
        SimpleDateFormat timePath = new SimpleDateFormat("yyyyMMdd");

        final String fileName = file.getOriginalFilename();

        final String dirName = dirPath + "/" + timePath.format(new Date());

        final String filePath = dirName + "/" + fileName;

        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType(file.getContentType());
        omd.setContentLength(file.getSize());
//        omd.setHeader(HEADER_FILE_NAME, fileName);


        System.out.println("filePath :: " + filePath);

        // 파일 업로드
        try {
            s3Client.putObject(new PutObjectRequest(bucket, filePath, file.getInputStream(), omd)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            e.printStackTrace();
            throw new S3UploadException();
        }

        return filePath;
    }

    public void delete() {
        // key 가 존재하면 기존 파일은 삭제
//        if (!StringUtils.isEmpty(currentFilePath)) {
//            boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);
//
//            if (isExistObject) {
//                s3Client.deleteObject(bucket, currentFilePath);
//            }
//        }

    }

}
