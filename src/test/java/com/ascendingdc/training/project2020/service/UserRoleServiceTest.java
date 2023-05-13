package com.ascendingdc.training.project2020.service;

//import com.ascending.training.init.AppInitializer;
import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= AppInitializer.class)
@SpringBootTest
public class UserRoleServiceTest {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void findAllUsersTest() {
        List<UserDto> userDtoList = userService.getAllUsers();
        displayUsers(userDtoList);
    }

    @Test
    public void encryptPassword() {
        String encryptPasswordFor123456789 = DigestUtils.md5Hex("123456789");
        logger.info("Hashed Password = {}", encryptPasswordFor123456789);
        String expectedEncryptPassword123456789 = "25f9e794323b453885f5181f1b624d0b";
        assertEquals(expectedEncryptPassword123456789, encryptPasswordFor123456789, "the encrypt password for 123456789 should be equal");
    }

    private void displayUsers(List<UserDto> userDtoList) {
        int index = 1;
        for(UserDto userDto : userDtoList) {
            displayUser(index++, userDto);
        }
    }

    private void displayUser(int i, UserDto userDto) {
        logger.info("======= User No.{} ========", i);
        logger.info("\t user.id={}", userDto.getId());
        logger.info("\t user.name={}", userDto.getName());
        logger.info("\t user.firstName={}", userDto.getFirstName());
        logger.info("\t user.lastName={}", userDto.getLastName());
        logger.info("\t user.email={}", userDto.getEmail());
        logger.info("\t user.secretKey={}", userDto.getSecretKey());
        Set<RoleDto> roleDtoSet = userDto.getRoleDtoSet();
        displayRoles(roleDtoSet);
    }

    private void displayRoles(Set<RoleDto> roleDtoSet) {
        int index = 1;
        for(RoleDto roleDto : roleDtoSet) {
            displayRole(index++, roleDto);
        }
    }

    private void displayRole(int i, RoleDto roleDto) {
        logger.info("\t######## Role No.{} ##########", i);
        logger.info("\t\t role.id={}", roleDto.getId());
        logger.info("\t\t role..name={}", roleDto.getName());
        logger.info("\t\t role.allAllowedResource={}", roleDto.getAllowedResource());
    }

}
