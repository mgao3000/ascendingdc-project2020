package com.ascendingdc.training.project2020.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.ascendingdc.training.project2020.service.AWSS3Service;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    public void setAmazonS3(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public AWSS3ServiceImpl() {
//        amazonS3 = getS3ClientUsingDefaultChain();
//        amazonS3 = getS3ClientWithEnvironmentCredentials();
//        amazonS3 = getS3ClientWithSuppliedCredentials();
    }

    @Value("${aws.accessKeyId}")
    private String myAWSAccessKeyId;
    @Value("${aws.secretKey}")
    private String myAWSSecretKey;

    private AmazonS3 getS3ClientWithSuppliedCredentials() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(myAWSAccessKeyId, myAWSSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1")
                .build();
        return s3Client;
    }

    private AmazonS3 getS3ClientUsingDefaultChain() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
        return s3Client;
    }

    private AmazonS3 getS3ClientWithEnvironmentCredentials() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();
        return s3Client;
    }

    public boolean isBucketExist(String bucketName) {
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        return isExist;
    }

    public boolean isObjectExist(String bucketName, String objectKey) {
        boolean isExist = amazonS3.doesObjectExist(bucketName, objectKey);
        return isExist;
    }

    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
        if(!amazonS3.doesBucketExistV2(bucketName)) {
            bucket = amazonS3.createBucket(bucketName);
        } else {
            logger.info("bucket name: {} is not available."
                    + " Try again with a different Bucket name.", bucketName);
        }

        return bucket;
    }

    public List<Bucket> getBucketList() {
        List<Bucket> buckets = amazonS3.listBuckets();
        return buckets;
    }

    public void deleteBucket(String bucketName) {
        try {
            amazonS3.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadMultipartFile(bucketName,file);
    }

    public String uploadMultipartFile(String bucketName,
           MultipartFile multipartFile) throws IOException {
        /*
        add some metadata to the uploaded object
         */
        //create a rendom object name
        logger.info("#####----- line No.1 before calling amazonS3.putObject!!!");
        String uuid = UUID.randomUUID().toString();
        logger.info("#####----- line No.2 before calling amazonS3.putObject!!!");
        String oriFilename = multipartFile.getOriginalFilename();
        logger.info("#####----- line No.3 before calling amazonS3.putObject!!!");

        int dotIndex = oriFilename.indexOf(".");
        String newRandomFilename = oriFilename.substring(0, dotIndex)
                + "_" + uuid + oriFilename.substring(dotIndex);
        logger.info("#####----- line No.4 before calling amazonS3.putObject!!!, newRandomFilename={}", newRandomFilename);
        logger.info("#####----- line No.4 before calling amazonS3.putObject!!!, bucketName={}", bucketName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        logger.info("#####----- line No.5 before calling amazonS3.putObject!!!");
        amazonS3.putObject(bucketName, newRandomFilename,
                multipartFile.getInputStream(), objectMetadata);
//        logger.info(String.format("The file name=%s was uploaded to bucket %s",
//                newRandomFilename, bucketName));
        return newRandomFilename;
    }

    public void uploadObject(String bucketName,
             String key, String fullFilepath) {
        amazonS3.putObject(bucketName, key,
                new File(fullFilepath));
    }

    public ObjectListing listObjects(String bucketName) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
//        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
//            logger.info("Object Key = {}", os.getKey());
//        }
        return objectListing;
    }

    public List<String> findObjectKeyList(String bucketName) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        List<String> objectKeyList = new ArrayList<String>();
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            objectKeyList.add(os.getKey());
        }
        return objectKeyList;
    }

    public File downloadObject(String bucketName,
        String objectKey, String destinationFullPath) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        File destFile = new File(destinationFullPath);
        FileUtils.copyInputStreamToFile(inputStream, destFile);
        return destFile;
    }

    public void copyObject(String oriBucketName,
        String oriObjectKey, String destBucketName,
        String destObjectKey) {
        amazonS3.copyObject(oriBucketName, oriObjectKey,
                destBucketName, destObjectKey);
    }

    public void copyObjectUsingCopyObjectRequest(
            String oriBucketName,
            String oriObjectKey,
            String destBucketName,
            String destObjectKey) {
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest();
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(oriBucketName,
            oriObjectKey, destBucketName, destObjectKey);
//        copyObjectRequest.setSourceBucketName(oriBucketName);
//        copyObjectRequest.setSourceKey(oriObjectKey);
//        copyObjectRequest.setDestinationBucketName(destBucketName);
//        copyObjectRequest.setDestinationKey(destObjectKey);
        amazonS3.copyObject(copyObjectRequest);
    }

    public void deleteObject(String bucketName, String objectKey) {
        amazonS3.deleteObject(bucketName, objectKey);
    }

    /*
        There are no directly ways to move and rename
        objects. Use the combination of copyObject and
        deleteObject accomplish the operations
     */

    public void deleteMultipleObjects(String bucketName,
         String[] objectKeyArray) {
        /*
        create DeleteObjectsRequest,
         */
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName)
                .withKeys(objectKeyArray);
        amazonS3.deleteObjects(deleteObjectsRequest);

    }
    public String generatePresignedURLForUploading(String bucketName,
                                       String objectKey) {
//        return generatePresignedURL(bucketName, objectKey, "PUT");
        return generatePresignedURL(bucketName, objectKey, "PUT");
    }

    public String generatePresignedURLForDownloading(String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public String generatePresignedURLForDownloading(String bucketName,
                                                   String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public String generatePresignedURL(String bucketName,
          String objectKey, String httpMethodString) {
        // Set the pre-signed URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL.
        logger.info("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.valueOf(httpMethodString))
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
