package com.lyf.multithreading.controller;

import com.lyf.multithreading.service.MultithreadingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @describe 多线程事物测试方法Controller
 * @Author joyous
 * @Date 2023/5/23
 */
@RestController
@Api(tags = "多线程测试API")
@RequestMapping("/multithreading/transaction")
public class MultithreadingController {
    @Resource
    MultithreadingService multithreadingService;

    @GetMapping("/multithreading-transaction-test")
    @ApiOperation(value = "测试多线程事务 （一个线程出现异常所有线程同时回滚）")
    public void multithreadingTransactionTest() {
        multithreadingService.multithreadingTransactionTest();
    }
}
