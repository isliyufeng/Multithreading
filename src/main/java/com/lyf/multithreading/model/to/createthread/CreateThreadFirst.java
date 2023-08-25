package com.lyf.multithreading.model.to.createthread;

import com.lyf.multithreading.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @describe
 * @Author joyous
 * @Date 2023/8/24
 */
@Slf4j
@Component
public class CreateThreadFirst extends Thread {
    @Resource
    SysUserService sysUserService;

    @Override
    public void run() {
        log.info("CreateThreadFirst ===================> 进入线程run方法内");
        sysUserService.addTest("创建线程方法1", 1);
        log.info("CreateThreadFirst ===================> 线程run方法内执行完毕");
    }
}
