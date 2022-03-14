package com.server.whaledone.config.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class S3FileManager {

    private final AmazonS3Client s3Client;

//    @Value("${cloud.aws.s3.bucket")
    private String bucketName = "whaledone-server-dev";

    public String getPresignedUrl(String fileName) {
        String filePath = "server-contents/" + fileName;

        Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, filePath)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        URL presignedURL = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        System.out.println("Pre-Signed URL: " + presignedURL.toString());


        return presignedURL.toExternalForm();
    }
}
