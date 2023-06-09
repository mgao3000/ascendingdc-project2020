package com.ascendingdc.training.project2020.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ascendingdc.training.project2020.service.impl.AWSS3ServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AWSS3FileMockTest {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired

//    private AmazonS3 amazonS3Mock = mock(AmazonS3.class);

    @Mock
    private AmazonS3 amazonS3Mock;

    @InjectMocks
    private AWSS3ServiceImpl awsS3Service;

    private Bucket mockBucket;

    @BeforeEach
    public void init() {
        //Mocks are initialized before each test method
        MockitoAnnotations.openMocks(this);
        mockBucket = mock(Bucket.class);
//        awss3Service = new AWSS3Service();
//        awss3Service.setAmazonS3(amazonS3Mock);
    }

    @Test
    public void testCreateBucket() {
//        String bucketName = "test-bucket-name";
//        when(amazonS3Mock.createBucket(anyString())).thenReturn(new Bucket(bucketName));
//        Bucket newBucket = awsS3Service.createBucket(bucketName);
//        assertEquals(bucketName, newBucket.getName());
//        verify(amazonS3Mock, timeout(500)).createBucket(anyString());
//        verify(amazonS3Mock, times(1)).createBucket(anyString());

        when(amazonS3Mock.createBucket(anyString())).thenReturn(mockBucket);
        Bucket newBucket = awsS3Service.createBucket(anyString());
        assertNotNull(newBucket);
        verify(amazonS3Mock, timeout(500)).createBucket(anyString());
        verify(amazonS3Mock, times(1)).createBucket(anyString());
    }

    @Test
    public void testCreateBucketWithArgumentCapture() {
        String bucketName = "test-bucket-name";
        when(amazonS3Mock.createBucket(anyString())).thenReturn(new Bucket(bucketName));
        ArgumentCaptor<String> argumentStringCaptor = ArgumentCaptor.forClass(String.class);

        Bucket newBucket = awsS3Service.createBucket(bucketName);

        assertEquals(bucketName, newBucket.getName());

        verify(amazonS3Mock, times(1)).createBucket(anyString());

        verify(amazonS3Mock).createBucket(argumentStringCaptor.capture());
        String argumentString = argumentStringCaptor.getValue();
        assertEquals(bucketName, argumentString);
    }

    @Test
    public void testBucketDoExist() {
        when(amazonS3Mock.doesBucketExistV2(anyString())).thenReturn(Boolean.TRUE);

        boolean bucketExist = awsS3Service.isBucketExist(anyString());

        assertTrue(bucketExist);
        verify(amazonS3Mock, times(1)).doesBucketExistV2(anyString());
    }

    @Test
    public void testDeleteBucket() {
        doNothing().when(amazonS3Mock).deleteBucket(anyString()); //optional

        awsS3Service.deleteBucket("xxx");

        verify(amazonS3Mock, times(1)).deleteBucket(anyString());
    }

//    @Test
//    public void testUploadMultipartFile() throws IOException {
//        InputStream spyInputStream = spy(InputStream.class);
//        InputStream mockInputStream = mock(InputStream.class);
////        ObjectMetadata mockObjectMetadata = mock(ObjectMetadata.class);
//
//        String filename = "/home/michael/Documents/downloads/File2.txt";
//        File file = new File(filename);
//        FileInputStream fileInputStream = new FileInputStream(file);
//
////        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
////                file.getName(), "text/plain",
////                IOUtils.toByteArray(fileInputStream));
//
//////        when(spyInputStream.read(spy(byte[].class))).thenReturn(anyInt());
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
//                "xxx", "xxx",
//                "xxx".getBytes());
//
//        assertNotNull(mockMultipartFile);
//        when(amazonS3Mock.putObject(eq("multipartFile"), anyString(),
//                any(InputStream.class),
//                any(ObjectMetadata.class))).thenReturn(any(PutObjectResult.class));
//
//        assertNotNull(awsS3Service);
//
//        awsS3Service.uploadMultipartFile(anyString(), mock(MultipartFile.class));
//
//        verify(amazonS3Mock, times(1)).putObject(anyString(),
//                anyString(), any(InputStream.class), any(ObjectMetadata.class));
//    }

    @Test
    public void testUploadMultipartFile() throws IOException {
//        InputStream inputStream = mock(InputStream.class);
//        ObjectMetadata objectMetadata = mock(ObjectMetadata.class);
        String filename = "/home/michael/Documents/downloads/File2.txt";
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
                file.getName(), "text/plain",
                IOUtils.toByteArray(fileInputStream));

        assertNotNull(mockMultipartFile);

        when(amazonS3Mock.putObject(anyString(), anyString(),
                any(InputStream.class),
                any(ObjectMetadata.class))).thenReturn(mock(PutObjectResult.class));

        assertNotNull(awsS3Service);

        awsS3Service.uploadMultipartFile("xxx", mockMultipartFile);

        verify(amazonS3Mock, times(1)).putObject(anyString(),
                anyString(), any(InputStream.class), any(ObjectMetadata.class));
    }

}
