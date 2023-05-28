package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.service.JWTService;
import com.ascendingdc.training.project2020.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/auth")
public class AuthOneController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> authenticate(@RequestBody UserDto userDto) {
        //1. validate username/password
        //400 bad request
        //2. generate token
        //200 or 500

        String token = "";
        Map<String, String> resultMap = new HashMap<>();
        try {
            UserDto retrievedUserDto = userService.getUserByCredentials(userDto.getEmail(), userDto.getPassword());
            displayUserDto(retrievedUserDto); 
            if(retrievedUserDto == null) {
                resultMap.put("msg", "The input user email and password are incorrect");
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(resultMap);
            }

            token = jwtService.generateToken(retrievedUserDto);
            resultMap.put("token", token);

        } catch (Exception e) {
            logger.error("Authenticate user failed, error = {}", e.getMessage());
            String errorMsg = e.getMessage();
            resultMap.put("msg", errorMsg);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resultMap);
        }

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(resultMap);

    }

    private void displayUserDto(UserDto retrievedUserDto) {
        logger.info("=== Within AuthOneController, userDto={}", retrievedUserDto);
        displayRoleDtoList(retrievedUserDto.getRoleDtoSet());
    }

    private void displayRoleDtoList(Set<RoleDto> roleDtoSet) {
        logger.info("\t ===== Within AuthOneController, the total number of retrieved Roles = {}", roleDtoSet.size());
        int index = 1;
        for(RoleDto roleDto : roleDtoSet) {
            logger.info("\t\t === Within AuthOneController, RoleDto No.{}, Role={}", index++, roleDto);
        }
    }
}
