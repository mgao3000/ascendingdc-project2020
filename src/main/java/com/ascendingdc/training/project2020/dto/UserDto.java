package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private long id;

    private String name;

    private String password;

    private String secretKey;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private Set<RoleDto> roleDtoSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDto> getRoleDtoSet() {
        return roleDtoSet;
    }

    public void setRoleDtoSet(Set<RoleDto> roleDtoSet) {
        this.roleDtoSet = roleDtoSet;
    }

    public User convertUserDtoToUser() {
        User user = new User();
        user.setId(getId());
        user.setName(getName());
        user.setPassword(getPassword());
        user.setSecretKey(getSecretKey());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setEmail(getEmail());
        user.setRoles(getRolesByRoleDtoSet(getRoleDtoSet()));
        return user;
    }

    private Set<Role> getRolesByRoleDtoSet(Set<RoleDto> roleDtoSet) {
        Set<Role> roleSet = new HashSet<>();
        for(RoleDto roleDto : roleDtoSet) {
            Role role = convertRoleDtoToRoleWithoutUser(roleDto);
            roleSet.add(role);
        }
        return roleSet;
    }

    private Role convertRoleDtoToRoleWithoutUser(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setAllowedResource(roleDto.getAllowedResource());
        role.setAllowedRead(roleDto.isAllowedRead());
        role.setAllowedCreate(roleDto.isAllowedCreate());
        role.setAllowedUpdate(roleDto.isAllowedUpdate());
        role.setAllowedDelete(roleDto.isAllowedDelete());
//        role.setUsers(getusersByUserDtoSet(roleDto.getUserDtoSet()));
        return role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
