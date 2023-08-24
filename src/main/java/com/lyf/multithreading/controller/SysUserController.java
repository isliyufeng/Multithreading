package com.lyf.multithreading.controller;


import com.lyf.multithreading.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @describe
 * @Author joyous
 * @Date 2023/5/23
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {
    @Resource
    SysUserService sysUserService;

    @GetMapping("/add-test")
    public Boolean addTest() {
        return sysUserService.addTest("test", 1);
    }
}
