package com.lyf.multithreading.model.to.createthread;

import com.lyf.multithreading.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * @describe
 * @Author joyous
 * @Date 2023/8/25
 */
@Slf4j
@Component
public class CreateThreadThird implements Callable<Boolean> {
    @Resource
    SysUserService sysUserService;

    @Override
    public Boolean call() throws Exception {
        Thread.sleep(5000);
        log.info("CreateThreadThird ===================> 进入线程run方法内");
        boolean result = sysUserService.addTest("创建线程方法3", 3);
        log.info("CreateThreadThird ===================> 线程run方法内执行完毕");
        return result;
    }
}
