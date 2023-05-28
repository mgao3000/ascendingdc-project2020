package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.aop.annotations.Loggable;
import com.ascendingdc.training.project2020.aop.annotations.TrackTime;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.exception.ItemNotFoundException;
import com.ascendingdc.training.project2020.exception.ExceptionResponse;
import com.ascendingdc.training.project2020.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/proj2020")
public class MajorController {

    private Logger logger = LoggerFactory.getLogger(MajorController.class);

    @Autowired
    private MajorService majorService;

    @Loggable
    @TrackTime
    @GetMapping(path="/majors", produces = "application/json")
    public List<MajorDto> getAllMajors() {
        List<MajorDto> majorDtoList = majorService.getMajors();
        return majorDtoList;
    }

    @GetMapping(path="/majors/{id}", produces = "application/json")
    public ResponseEntity<Object> getMajorByMajorId(@PathVariable("id") Long majorId)  throws ItemNotFoundException {
        MajorDto majorDto = null;
        ResponseEntity<Object> responseEntity = null;
        try {
            majorDto = majorService.getMajorById(majorId);
            responseEntity =  new ResponseEntity<Object>(majorDto, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException", LocalDateTime.now(), e.getMessage());
            responseEntity =  new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
//            throw new ItemNotFoundException(e.getMessage());
        }
        return responseEntity;
//        MajorDto majorDto = majorService.getMajorById(majorId);
//        if(majorDto == null) {
//            throw new EntityNotFoundException(String.format("Could not find Major with is=%d", majorId));
//        }
/////        return majorDto;
    }

    @GetMapping(path="/majors/name/{name}", produces = "application/json")
    public ResponseEntity<Object> getMajorByMajorName(@PathVariable("name") String majorName) {
        MajorDto majorDto = null;
        ResponseEntity<Object> responseEntity = null;
        try {
            majorDto = majorService.getMajorByName(majorName);
            responseEntity =  new ResponseEntity<Object>(majorDto, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException", LocalDateTime.now(), e.getMessage());
            responseEntity =  new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);

        }
        return responseEntity;
    }

    @PostMapping(path="/majors", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MajorDto> createMajor(@RequestBody MajorDto majorDto) {
        MajorDto savedMajorDto = majorService.save(majorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMajorDto.getId())
                .toUri();

        logger.info("===============, before return URI location = {}", location);
        return ResponseEntity.created(location).body(savedMajorDto);

//        if(majorDto.getId() != null) {
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"A new user cannot already have an ID");
//        }
//        return new ResponseEntity<MajorDto>(savedMajorDto, HttpStatus.CREATED);
////        return majorService.save(majorDto);
    }

    @PutMapping(path="/majors", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MajorDto> updateMajor(@RequestBody MajorDto majorDto) {
        MajorDto updatedMajorDto = majorService.update(majorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedMajorDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(updatedMajorDto);
    }

    @DeleteMapping(path="/majors/name/{name}", produces = "application/json")
    public ResponseEntity deleteMajorByMajorName(@PathVariable("name") String majorName) {
        boolean deleteResult = majorService.deleteByName(majorName);
        if(deleteResult)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping(path="/majors/{id}", produces = "application/json")
    public ResponseEntity deleteMajorByMajorId(@PathVariable("id") Long majorId) {
        boolean deleteResult = majorService.deleteById(majorId);
        if(deleteResult)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping(path="/majors", produces = "application/json")
    public ResponseEntity deleteMajorByMajorDto(@RequestBody MajorDto majorDto) {
        boolean deleteResult = majorService.delete(majorDto);
        if(deleteResult)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }


    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> itemNotFEx(WebRequest webRequest, Exception exception) {
        System.out.println("In CREEH::ItemNFE");
        ExceptionResponse exceptionResponse = new ExceptionResponse("Item Not Found Ex!!!", LocalDateTime.now(), webRequest.getDescription(false));
        ResponseEntity<ExceptionResponse> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

}
