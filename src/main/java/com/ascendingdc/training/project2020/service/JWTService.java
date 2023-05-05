package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.UserDto;
//import com.ascendingdc.training.project2020.entity.User;
import io.jsonwebtoken.Claims;

public interface JWTService {

    String generateToken(UserDto userDto);

    Claims decryptJwtToken(String token);

    boolean hasTokenExpired(String token);
}
