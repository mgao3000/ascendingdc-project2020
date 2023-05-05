package com.ascendingdc.training.project2020.dto;

import javax.persistence.Column;
import java.util.Set;

public class RoleDto {
    private long id;

    private String name;

    private String allowedResource;

    private boolean allowedRead;

    private boolean allowedCreate;

    private boolean allowedUpdate;

    private boolean allowedDelete;

    private Set<UserDto> userDtoSet;

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

    public Set<UserDto> getUserDtoSet() {
        return userDtoSet;
    }

    public void setUserDtoSet(Set<UserDto> userDtoSet) {
        this.userDtoSet = userDtoSet;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allowedResource='" + allowedResource + '\'' +
                ", allowedRead=" + allowedRead +
                ", allowedCreate=" + allowedCreate +
                ", allowedUpdate=" + allowedUpdate +
                ", allowedDelete=" + allowedDelete +
                '}';
    }
}
