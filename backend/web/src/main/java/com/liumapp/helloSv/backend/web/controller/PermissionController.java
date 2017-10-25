package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.model.Permission;
import com.liumapp.helloSv.backend.web.service.PermissionService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends GlobalController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/all")
    public List<Permission> getAll(Permission permission) {
        return permissionService.getAll(permission);
    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        Permission permission = permissionService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, permission);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        permissionService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody Permission permission) {
        String msg = permission.getId() == null ? "新增成功!" : "修改成功!";
        permissionService.save(permission);
        return new RespInfo(Consts.SUCCESS_CODE, permission, msg);
    }
}
