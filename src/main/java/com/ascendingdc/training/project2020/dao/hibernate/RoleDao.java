/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    Role save(Role role);
    boolean delete(Role role);
    List<Role> findAllRoles();
}
