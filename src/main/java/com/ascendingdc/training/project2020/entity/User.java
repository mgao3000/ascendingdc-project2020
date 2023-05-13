/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.entity;

import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    public User() { }
    public User(String name, String password, String secretKey, String firstName, String lastName, String email) {
        this.name = name;
        this.password = password;
        this.secretKey = secretKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    //@SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "user_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    //@ManyToMany(mappedBy = "users", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
//    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles;

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
    //User u = new User()
    //String digestPassword=DigestUtils.md5Hex(password.trim());
    //u.setPassword(digestPassword)
    //session.save(u)
    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password.trim());
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
        this.email = email.toLowerCase().trim();
    }

    public Set<Role> getRoles() {
        if(roles == null)
            roles = new HashSet<Role>();
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }

    public UserDto convertUserToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(getId());
        userDto.setName(getName());
        userDto.setPassword(getPassword());
        userDto.setSecretKey(getSecretKey());
        userDto.setFirstName(getFirstName());
        userDto.setLastName(getLastName());
        userDto.setEmail(getEmail());
        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(getRoles()));
        return userDto;
    }

    private Set<RoleDto> getRoleDtoSetByRolesWithoutUserDto(Set<Role> roles) {
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for(Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDtoWithoutUser(role);
            roleDtoSet.add(roleDto);
        }
        return roleDtoSet;
    }

    private static RoleDto convertRoleToRoleDtoWithoutUser(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setAllowedResource(role.getAllowedResource());
        roleDto.setAllowedRead(role.isAllowedRead());
        roleDto.setAllowedCreate(role.isAllowedCreate());
        roleDto.setAllowedUpdate(role.isAllowedUpdate());
        roleDto.setAllowedDelete(role.isAllowedDelete());
//        roleDto.setUserDtoSet(getUserDtoSetByUsers(role.getUsers()));
        return roleDto;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, secretKey, firstName, lastName, email);
    }

//    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }

    @Override
    public String toString() {
        return "User{" +
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
