package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.Account;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.service.AccountService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/account")
public class AccountController extends GlobalController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/all")
    public RespInfo getAll(Account account) throws Exception {
        return new RespInfo(Consts.SUCCESS_CODE, accountService.getAll(account));
    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        Account account = accountService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, account);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        accountService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody Account account) {
        String msg = account.getId() == null ? "新增成功!" : "修改成功!";
        accountService.save(account);
        return new RespInfo(Consts.SUCCESS_CODE, account, msg);
    }

    @RequestMapping(value = "/rejectAccountStatus", method = RequestMethod.POST)
    public RespInfo rejectAccountStatus(@RequestBody Account account) throws BizException {
        accountService.rejectAccountStatus(account);
        return new RespInfo(Consts.SUCCESS_CODE, account, "拒绝成功");
    }


}