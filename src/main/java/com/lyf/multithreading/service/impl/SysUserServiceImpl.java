package com.lyf.multithreading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.multithreading.mapper.SysUserMapper;
import com.lyf.multithreading.model.entity.SysUser;
import com.lyf.multithreading.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @describe
 * @Author joyous
 * @Date 2023/5/23
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public boolean addTest(String name, Integer age) {
//        if (age % 2 == 0) {
//            log.error("年龄为 {}, 是偶数，返回FALSE", age);
//            return false;
//        }

        SysUser sysUser = new SysUser();
        sysUser.setName(name);
        sysUser.setAge(age);
        log.debug("姓名为：{} 的用户添加成功", name);
        return this.save(sysUser);
    }
}
