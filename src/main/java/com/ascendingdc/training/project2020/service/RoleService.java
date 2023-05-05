package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto getRoleByName(String name);

    List<RoleDto> getAllRoles();
}
