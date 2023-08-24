package com.lyf.multithreading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyf.multithreading.model.entity.SysUser;

/**
 * @Describe
 * @Author joyous
 * @Date 2023/5/23
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 添加用户
     * @param name 姓名
     * @param age 年龄
     */
    boolean addTest(String name, Integer age);
}
