package com.ascendingdc.training.project2020.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
//@Profile({"dev","prod", "dev123"})
public class AWSConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKeyId}")
    private String myAWSAccessKeyId;

    @Value("${aws.secretKey}")
    private String myAWSSecretKey;


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3() {
//        return  AmazonS3ClientBuilder
//                .standard().withCredentials(new DefaultAWSCredentialsProviderChain())
//                .withRegion(awsRegion)
//                .withRegion(Regions.US_EAST_1)
//                .build();

        return getS3ClientWithSuppliedCredentials();
    }

//    private String myAWSAccessKeyId="AKIAUH7NDCSMMAMIRDT3";
//    private String myAWSSecretKey="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";

    private AmazonS3 getS3ClientWithSuppliedCredentials() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(myAWSAccessKeyId, myAWSSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(awsRegion)
                .build();
        return s3Client;
    }

    private AmazonS3 getS3ClientUsingDefaultChain() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(awsRegion)
                .build();
        return s3Client;
    }

    private AmazonS3 getS3ClientWithEnvironmentCredentials() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(awsRegion)
                .build();
        return s3Client;
    }

//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public AmazonSQS getAmazonSQS() {
//        return AmazonSQSClientBuilder
//                .standard()
//                .withCredentials(new DefaultAWSCredentialsProviderChain())
//                .withRegion(Regions.US_EAST_1)
//                .build();
//    }

}
