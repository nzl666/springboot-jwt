package com.liumapp.helloSv.backend.web.mapper;

import com.liumapp.helloSv.backend.web.model.User;
import com.liumapp.helloSv.backend.web.model.Permission;
import com.liumapp.helloSv.backend.web.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public interface UserMapper extends MyMapper<User> {
    List<Permission> pullPermissions(@Param("name") String name);

    void updateStatus(User user);
}
