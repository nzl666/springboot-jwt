package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.UserMapper;
import com.liumapp.helloSv.backend.web.model.Permission;
import com.liumapp.helloSv.backend.web.model.User;
import com.liumapp.helloSv.backend.web.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAll() {
        return userMapper.selectAll();
    }

    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public void save(User user) {
        if (user.getId() != null) {
            user.setUpdateDate(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            user.setStatus(Status.VALID);
            user.setCreateDate(new Date());
            user.setLastLogin(new Date());
            userMapper.insert(user);
        }
    }

    public void updateStatus(User user) {
        userMapper.updateStatus(user);
    }

    public int updatePasswordByName(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("name=", user.getName());
        user.setUpdateDate(new Date());
        return userMapper.updateByExampleSelective(user, example);
    }

    public boolean checkPassword(String name, String password) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("name=", name).andCondition("password=", password);
        return userMapper.selectCountByExample(example) == 1;
    }

    public User getByNamePwd(User user) {
        return userMapper.selectOne(user);
    }

    public List<Permission> pullPermissions(String name) {
        return userMapper.pullPermissions(name);
    }

    public boolean ifExist(String name) {
        User user = new User();
        user.setName(name);
        return userMapper.selectCount(user) != 0;
    }
}
