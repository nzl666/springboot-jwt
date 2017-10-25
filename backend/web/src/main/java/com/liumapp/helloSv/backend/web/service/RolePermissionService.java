package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.RolePermissionMapper;
import com.liumapp.helloSv.backend.web.model.Role;
import com.liumapp.helloSv.backend.web.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public List<RolePermission> getAll(RolePermission rolePermission) {
        return rolePermissionMapper.selectAll();
    }

    public List<RolePermission> getById(Integer id) {
        Example example = new Example(Role.class);
        example.createCriteria().andCondition("rid=", id);
        return rolePermissionMapper.selectByExample(example);
    }

    public void deleteById(Integer roleId) {
        Example example = new Example(Role.class);
        example.createCriteria().andCondition("rid=", roleId);
        rolePermissionMapper.deleteByExample(example);
    }

    public void saveList(List<RolePermission> rolePermissions) {
        rolePermissionMapper.insertList(rolePermissions);
    }

    public void save(RolePermission rolePermission) {
        rolePermissionMapper.insert(rolePermission);
    }
}
