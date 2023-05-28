package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.exception.ExceptionResponse;
import com.ascendingdc.training.project2020.exception.ItemNotFoundException;
import com.ascendingdc.training.project2020.service.MajorService;
import com.ascendingdc.training.project2020.service.ProjectService;
import com.ascendingdc.training.project2020.service.StudentService;
import org.postgresql.largeobject.BlobOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/project2021")
public class MajorOneController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MajorService majorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectService projectService;

//    @GetMapping(value = "/majors", produces = "application/json")
    @GetMapping(value = "/majors", produces = "application/json", headers = {"name=mike", "id=101"})
    public ResponseEntity<List<MajorDto>> findAllMajors() {
        List<MajorDto> majorDtoList = majorService.getMajors();
//        return majorDtoList;
        return new ResponseEntity<>(majorDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/majors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findMajorDtoByMajorId(@PathVariable("id") Long majorId) {
        MajorDto majorDto = null;
        ResponseEntity<Object> responseEntity = null;
        try {
            majorDto = majorService.getMajorById(majorId);
            responseEntity = new ResponseEntity<>(majorDto, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException",
                    LocalDateTime.now(), e.getMessage());
            responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping(value = "/majors/param_practice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findMajorDtoByRequestParamMajorId(@RequestParam("id") Long majorId) {
        MajorDto majorDto = null;
        ResponseEntity<Object> responseEntity = null;
        try {
            majorDto = majorService.getMajorById(majorId);
            responseEntity = new ResponseEntity<>(majorDto, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException",
                    LocalDateTime.now(), e.getMessage());
            responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping(value = "/majors/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findMajorDtoByMajorName(@PathVariable("name") String majorName) {
        MajorDto majorDto = null;
        ResponseEntity<Object> responseEntity = null;
        try {
            majorDto = majorService.getMajorByName(majorName);
            responseEntity = new ResponseEntity<>(majorDto, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException",
                    LocalDateTime.now(), e.getMessage());
            responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }

        return responseEntity;

    }

    @PostMapping(value = "/majors", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MajorDto> saveMajorDto(@RequestBody MajorDto majorDto) {
        MajorDto savedMajorDto = majorService.save(majorDto);

        URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMajorDto.getId())
                .toUri();

        logger.info("============, before return URI value = {}", uriLocation);
        return ResponseEntity.created(uriLocation).body(savedMajorDto);
//        return new ResponseEntity(savedMajorDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/majors", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MajorDto updateMajorDto(@RequestBody MajorDto majorDto) {
        MajorDto updatedMajorDto = majorService.update(majorDto);
        return updatedMajorDto;
    }

    @DeleteMapping(value = "/majors", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteMajorDto(@RequestBody MajorDto majorDto) {
        boolean deleteResult = majorService.delete(majorDto);
        if(deleteResult) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

}
