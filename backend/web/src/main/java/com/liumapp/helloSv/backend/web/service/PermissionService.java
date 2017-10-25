package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.PermissionMapper;
import com.liumapp.helloSv.backend.web.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> getAll(Permission permission) {
        return permissionMapper.selectAll();
    }

    public Permission getById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    public void save(Permission permission) {
        if (permission.getId() != null) {
            permissionMapper.updateByPrimaryKey(permission);
        } else {
            permissionMapper.insert(permission);
        }
    }
}

