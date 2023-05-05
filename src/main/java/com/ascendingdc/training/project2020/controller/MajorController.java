package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proj2020")
public class MajorController {

    private Logger logger = LoggerFactory.getLogger(MajorController.class);

    @Autowired
    private MajorService majorService;

    @GetMapping(path="/majors", produces = "application/json")
    public List<MajorDto> getAllMajors() {
        List<MajorDto> majorDtoList = majorService.getMajors();
        return majorDtoList;
    }

    @GetMapping(path="/majors/{id}", produces = "application/json")
    public MajorDto getMajorByMajorId(@PathVariable("id") Long majorId) {
        MajorDto majorDto = majorService.getMajorById(majorId);
        return majorDto;
    }

    @GetMapping(path="/majors/name/{name}", produces = "application/json")
    public MajorDto getMajorByMajorName(@PathVariable("name") String majorName) {
        MajorDto majorDto = majorService.getMajorByName(majorName);
        return majorDto;
    }

    @PostMapping(path="/majors", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MajorDto> createMajor(@RequestBody MajorDto majorDto) {
        MajorDto savedMajorDto = majorService.save(majorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMajorDto.getId())
                .toUri();

        return ResponseEntity.created(location).build();

//        if(userDto.getId() != null) {
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"A new user cannot already have an ID");
//        }
//        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PutMapping(path="/majors", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MajorDto> updateMajor(@RequestBody MajorDto majorDto) {
        MajorDto updatedMajorDto = majorService.update(majorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedMajorDto.getId())
                .toUri();

        return ResponseEntity.created(location).build();
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



}
