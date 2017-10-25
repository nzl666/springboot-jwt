package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.model.Role;
import com.liumapp.helloSv.backend.web.mapper.RoleMapper;
import com.liumapp.helloSv.backend.web.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getAll(Role role) {
        return roleMapper.selectAll();
    }

    public Role getById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    public void save(Role role) {
        if (role.getId() != null) {
            roleMapper.updateByPrimaryKey(role);
        } else {
            role.setStatus(Status.VALID);
            roleMapper.insert(role);
        }
    }

    public void updateStatus(Role role) {
        roleMapper.updateStatus(role);
    }
}
