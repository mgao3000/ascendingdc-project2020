package com.ascendingdc.training.project2020.service;


import com.ascendingdc.training.project2020.dao.hibernate.UserDao;
import com.ascendingdc.training.project2020.entity.User;
//import com.ascending.training.init.AppInitializer;
import com.ascendingdc.training.project2020.dto.UserDto;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= AppInitializer.class)
@SpringBootTest
public class JWTServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDao userDao;

    private User user;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        String username = "dwang"; // ""rhang";
        user = userDao.getUserByName(username);
        userDto = user.convertUserToUserDto();
    }

    @Test
    public void generateTokenTest(){
//        String username = "dwang"; // ""rhang";
//        User user = userDao.getUserByName(username);

//        User u = new User();
//        u.setId(1L);
//        u.setName("ryohang");
        String token = jwtService.generateToken(userDto);
//        assertion
        assertNotNull(token);
        String[] tArray = token.split("\\.");
        assertEquals(tArray.length,3);
        logger.info("@@@###$$$, generated token is: {}", token);
    }

    @Test
    public void decryptJwtTokenTest(){
//        User u = new User();
//        u.setId(1L);
//        u.setName("ryohang");

//        String username = "dwang"; // ""rhang";
//        User user = userDao.getUserByName(username);

        String token = jwtService.generateToken(userDto);
        Claims claims = jwtService.decryptJwtToken(token);
        String retrievedUsername = claims.getSubject();
        assertEquals(user.getName(),retrievedUsername);
        logger.info("=== from decryptJwtTokenTest(),  Claims: " + claims.toString());

    }

    @Test
    public void passwordDigestMD5HexTest() {
        String passwordInPlainText = "123456789";
        String passwordEncryptedWithMD5Hex = DigestUtils.md5Hex(passwordInPlainText);
        logger.info("=====, the original password value is: {}, after MD5HEX encrypted, the value becomes {}",
                passwordInPlainText, passwordEncryptedWithMD5Hex);
    }

    @Test
    public void tokenHasNotExpiredTest() {
        String token = jwtService.generateToken(userDto);
        assertNotNull(token);

        boolean tokenExpirationFlag = jwtService.hasTokenExpired(token);
        assertFalse(tokenExpirationFlag);
    }

}
