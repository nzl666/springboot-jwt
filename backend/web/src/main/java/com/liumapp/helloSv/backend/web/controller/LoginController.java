package com.liumapp.helloSv.backend.web.controller;

import com.huluwa.cc.jwt.Jwt;
import com.huluwa.cc.jwt.ProjectToken;
import com.liumapp.helloSv.backend.web.model.User;
import com.liumapp.helloSv.backend.web.service.UserService;
import com.liumapp.helloSv.backend.web.utils.CacheUtil;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
public class LoginController extends GlobalController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectToken projectToken;

    @RequestMapping(value = "/login/{timestamp}")
    public RespInfo login(@RequestBody User user, @PathVariable String timestamp) {
        User u = userService.getByNamePwd(user);
        int code = Consts.ERROR_CODE;
        String token = null;
        if (u != null) {
            code = Consts.SUCCESS_CODE;
            String username = user.getName();
            CacheUtil.putCache(username + timestamp, userService.pullPermissions(username));
            token = projectToken.generatingToken(u.getId().toString());
        }
        return new RespInfo(code, u, null,token);
    }

    @RequestMapping(value = "/pull/{key}")
    public RespInfo pull(@PathVariable String key) throws ExecutionException {

        return new RespInfo(Consts.SUCCESS_CODE, CacheUtil.getCache(key), null,null);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}

