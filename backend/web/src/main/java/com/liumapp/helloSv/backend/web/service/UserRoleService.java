package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.UserRoleMapper;
import com.liumapp.helloSv.backend.web.model.UserRole;
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
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<UserRole> getAll(UserRole userRole) {
        return userRoleMapper.selectAll();
    }

    public List<UserRole> getById(Integer id) {
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("uid=", id);
        return userRoleMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("uid=", id);
        userRoleMapper.deleteByExample(example);
    }

    public void save(UserRole userRole) {
        userRoleMapper.insert(userRole);
    }

    public void saveList(List<UserRole> userRoles) {
        userRoleMapper.insertList(userRoles);
    }

}
