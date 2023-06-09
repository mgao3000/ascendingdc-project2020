package com.ascendingdc.training.project2020.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AWSS3ServiceTest {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSS3Service s3Service;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String testBucketName;

    @Value("${aws.region}")
    private String awsRegion;

    @BeforeEach
    public void init() {
//        s3Client = getS3ClientWithSuppliedCredentials();
//////        s3Client = getS3ClientUsingDefaultChain();
////        s3Client = getS3ClientWithEnvironmentCredentials();
//////        s3Service.setAmazonS3(s3Client);

        testBucketName = "mgao-s3-bucket-06-06-01-2023";
    }

    private String myAWSAccessKeyId="AKIAUH7NDCSMMAMIRDT3";
    private String myAWSSecretKey="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";

    private AmazonS3 getS3ClientWithSuppliedCredentials() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(myAWSAccessKeyId, myAWSSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.US_EAST_1)
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

    @Test
    public void testIsBucketExist() {
        String bucketName = "class-1801-1";
        boolean isBucketExist = s3Service.isBucketExist(bucketName);
        assertTrue(isBucketExist);
    }

    @Test
    public void testCreateBucket() {
//        String bucketName = "mgao-s3-bucket-1-9-24-2020";
//        String bucketName = "mgao-sync-east-1";
        String bucketName = createBucketName("mgao-s3-bucket");
        Bucket bucket = s3Service.createBucket(bucketName);
        assertNotNull(bucket);
        assertThat(bucketName, is(equalTo(bucket.getName())));
        assertThat(true, is(s3Service.isBucketExist(bucketName)));
    }

    private String createBucketName(String bucketNamePrefix) {
        return bucketNamePrefix + "-" + getRandomInt(1, 1000);
    }

    @Test
    public void testListBucket() {
        List<Bucket> buckets = s3Service.getBucketList();
        for(Bucket bucket : buckets) {
            logger.info("bucket = {}", bucket.getName());
        }
    }

    @Test
    public void testDeleteBucketObjects() {
//        String bucketName = "mgao-s3-bucket-2";
        String bucketName = "class-1801-2-copy-5";
        String objectKey = "images/green-square.png";
        s3Service.deleteObject(bucketName, objectKey);
        assertThat(false, is(s3Service.isObjectExist(bucketName, objectKey)));
    }

    @Test
    public void testDeleteEmptyBucket() {
//        String bucketName = "mgao-s3-bucket-2";
        String bucketName = "class-1801-2-copy-5";
        s3Service.deleteBucket(bucketName);
        assertThat(false, is(s3Service.isBucketExist(bucketName)));
    }

    @Test
    public void testPresignedURLForUploading()  {
        String bucketName = "class-1801-1";
//        String filename = "images/plain-text-uploaded-2.txt";
        String filename = "temp/bucket-1/images/plain-text-uploaded-2.txt";
//        String filename = "/home/michael/temp/bucket-1/images/plain-text-uploaded.txt";
        String urlString = s3Service.generatePresignedURLForUploading(bucketName, filename);
        logger.info("==== testBucketName ={}", bucketName);
        logger.info("==== PUT presigneURL string ={}", urlString);
        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text uploaded as an object via presigned URL.");
            out.close();

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testPresignedURLForDownloading() {
        String bucketName = "debug-14-3";
//        String filename = "bucket-1/my-folder/my-sub-folder/my-text-file.txt";
//        String destFilename = "/home/michael/Documents/downloads/my-text-file-downloaded.txt";
        String filename = "bucket-1/images/star.jpg";
        String destFilename = "/home/michael/Documents/downloads/star-image-3.jpg";
        String urlString = s3Service.generatePresignedURLForDownloading(bucketName, filename);
        logger.info("==== GET presigneURL string ={}", urlString);

        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            File destFile = new File(destFilename);
            FileUtils.touch(destFile);
            InputStream is = connection.getInputStream();

            FileUtils.copyInputStreamToFile(is, destFile);

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

            IOUtils.closeQuietly(is);
            IOUtils.close(connection);

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testDirectlyUsingPresignedURLForDownloading() {
//        String bucketName = "mgao-s3-bucket-1";
//        String filename = "presignedTest.txt";
        String destFilename = "/home/michael/Documents/downloads/star-demo-112.png";
//        String urlString = "https://debug-14-1.s3.amazonaws.com/bucket-1/images/green-square.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201007T204251Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIAUH7NDCSMB4JXR7OC%2F20201007%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=674badfe9d797881073682e28c856baf6431c41fabb849bc39344d695c03051e";
        String urlString = "https://debug-14-3.s3.amazonaws.com/bucket-1/images/star.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230607T031646Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIAUH7NDCSMMAMIRDT3%2F20230607%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=11468deaeda0c82e9da949c5c43395fec3e65fec1d4cb3cdc1a84959732d4fe9";
        logger.info("==== GET presigneURL string ={}", urlString);

        try {
            URL url = new URL(urlString);
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            File destFile = new File(destFilename);
            FileUtils.touch(destFile);
            InputStream is = connection.getInputStream();

            FileUtils.copyInputStreamToFile(is, destFile);

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

            IOUtils.closeQuietly(is);
            IOUtils.close(connection);

        } catch (MalformedURLException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error(e.getMessage());
        } catch (IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testUploadMultipartFile() throws IOException {
//        String bucketName = "mgao-s3-bucket-1";
        String filename = "/home/michael/Documents/downloads/File2.txt";
        File file = new File(filename);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("multipartFile",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        String objectKey = s3Service.uploadMultipartFile(testBucketName, multipartFile);
        logger.info("===Uploaded multipartfile name is: {}", objectKey);

        List<String> objectKeyList = s3Service.findObjectKeyList(testBucketName);
//        assertTrue(objectKeyList.contains(objectKey));
        assertThat(true, is(objectKeyList.contains(objectKey)));
    }

    @Test
    public void testAwsRegionValue()  {
        assertThat("us-east-1", is(equalTo(awsRegion)));
        logger.info("#####, awsRegion = {}", awsRegion);
    }

    /**
     * This helper method return a random int value in a range between
     * min (inclusive) and max (exclusive)
     * @param min
     * @param max
     * @return
     */
    public int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}