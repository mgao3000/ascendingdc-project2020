/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.service.AWSS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    private static final String queueName = "training_queue_ascending_com";
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AWSS3Service fileService;
//    @Autowired
//    private AWSMessageService messageService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //you can return either s3 key or file url
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("test file name:"+file.getOriginalFilename());
        try {
           return fileService.uploadFile(file);
        } catch (IOException e) {
            logger.error("=====, upload file to AWS S3 failed, error={}", e.getMessage());
        }
        return null;
    }
//    @RequestMapping(value = "/{s3ObjectName}", method = RequestMethod.GET)
//    public ResponseEntity<String> getFileUrlForDownloading(@PathVariable("s3ObjectName") String fileName) {
    @GetMapping(params={"fileName"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFileUrlForDownloading(@RequestParam("fileName") String fileName) {
//        request.getSession()
//        Resource resource = null;
//        String msg = "The file doesn't exist.";

        logger.info("==============, getFileUrlForDownloading(...), input fileName = {}", fileName);
        ResponseEntity responseEntity;

        try {
            String presignedURL = fileService.generatePresignedURLForDownloading(fileName);
            logger.debug("==== retrieved PresignedURL = {}", presignedURL);
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_OK).body(presignedURL);
        }
        catch (Exception ex) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
            logger.debug(ex.getMessage());
        }

        return responseEntity;
    }


//    @RequestMapping(value = "/{bucketName}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadFile(@PathVariable String bucketName, @RequestParam("file") MultipartFile file) {
//        String msg = String.format("The file name=%s, size=%d could not be uploaded.", file.getOriginalFilename(), file.getSize());
//        ResponseEntity responseEntity = ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(msg);
//        try {
//            String url = fileService.uploadFile(file);
//            if (url != null) {
//                msg = String.format("The file name=%s, size=%d was uploaded, url=%s", file.getOriginalFilename(), file.getSize(), url);
//                messageService.sendMessage(queueName, url);
//                responseEntity = ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
//            }
//            logger.info(msg);
//        }
//        catch (Exception e) {
//            responseEntity = ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
//            logger.error(e.getMessage());
//        }
//
//        return responseEntity;
//    }

//    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        Resource resource = null;
//        String msg = "The file doesn't exist.";
//        ResponseEntity responseEntity;
//
//        try {
//            Path filePath = Paths.get(fileDownloadDir).toAbsolutePath().resolve(fileName).normalize();
//            resource = new UrlResource(filePath.toUri());
//            if(!resource.exists()) return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(msg);
//            responseEntity = ResponseEntity.ok()
//                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                             .body(resource);;
//
//            msg = String.format("The file %s was downloaded", resource.getFilename());
//            //Send message to SQS
//            messageService.sendMessage(queueName, msg);
//            logger.debug(msg);
//        }
//        catch (Exception ex) {
//            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
//            logger.debug(ex.getMessage());
//        }
//
//        return responseEntity;
//    }
}
