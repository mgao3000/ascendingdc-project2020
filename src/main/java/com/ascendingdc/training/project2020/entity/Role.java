/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.entity;

import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    //@SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "role_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "allowed_resource")
    private String allowedResource;

    @Column(name = "allowed_read")
    private boolean allowedRead;

    @Column(name = "allowed_create")
    private boolean allowedCreate;

    @Column(name = "allowed_update")
    private boolean allowedUpdate;

    @Column(name = "allowed_delete")
    private boolean allowedDelete;

    @JsonIgnore
    /*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    */
//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

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

    public String getAllowedResource() {
        return allowedResource;
    }

    public void setAllowedResource(String allowedResource) {
        this.allowedResource = allowedResource;
    }

    public boolean isAllowedRead() {
        return allowedRead;
    }

    public void setAllowedRead(boolean allowedRead) {
        this.allowedRead = allowedRead;
    }

    public boolean isAllowedCreate() {
        return allowedCreate;
    }

    public void setAllowedCreate(boolean allowedCreate) {
        this.allowedCreate = allowedCreate;
    }

    public boolean isAllowedUpdate() {
        return allowedUpdate;
    }

    public void setAllowedUpdate(boolean allowedUpdate) {
        this.allowedUpdate = allowedUpdate;
    }

    public boolean isAllowedDelete() {
        return allowedDelete;
    }

    public void setAllowedDelete(boolean allowedDelete) {
        this.allowedDelete = allowedDelete;
    }

    public Set<User> getUsers() {
        if(users == null)
            users = new HashSet<User>();
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.getUsers().add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        this.getUsers().remove(user);
        user.getRoles().remove(this);
    }


    public RoleDto convertRoleToRoleDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(getId());
        roleDto.setName(getName());
        roleDto.setAllowedResource(getAllowedResource());
        roleDto.setAllowedRead(isAllowedRead());
        roleDto.setAllowedCreate(isAllowedCreate());
        roleDto.setAllowedUpdate(isAllowedUpdate());
        roleDto.setAllowedDelete(isAllowedDelete());
        roleDto.setUserDtoSet(getUserDtoSetByUsers(getUsers()));
        return roleDto;
    }

    private static Set<UserDto> getUserDtoSetByUsers(Set<User> users) {
        Set<UserDto> userDtoSet = new HashSet<>();
        for(User user : users) {
            UserDto userDto = convertUserToUserDtoWithoutRole(user);
            userDtoSet.add(userDto);
        }
        return userDtoSet;
    }

    public static UserDto convertUserToUserDtoWithoutRole(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setSecretKey(user.getSecretKey());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
//        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(user.getRoles()));
        return userDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id &&
                allowedRead == role.allowedRead &&
                allowedCreate == role.allowedCreate &&
                allowedUpdate == role.allowedUpdate &&
                allowedDelete == role.allowedDelete &&
                name.equals(role.name) &&
                allowedResource.equals(role.allowedResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allowedResource, allowedRead, allowedCreate, allowedUpdate, allowedDelete);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allowedResource='" + allowedResource + '\'' +
                ", allowedRead=" + allowedRead +
                ", allowedCreate=" + allowedCreate +
                ", allowedUpdate=" + allowedUpdate +
                ", allowedDelete=" + allowedDelete +
                '}';
    }

    //    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }
}
