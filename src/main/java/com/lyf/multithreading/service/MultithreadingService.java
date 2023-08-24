package com.lyf.multithreading.service;

/**
 * @Describe
 * @Author joyous
 * @Date 2023/5/23
 */
public interface MultithreadingService {
    /**
     * 测试多线程事务 （一个线程出现异常所有线程同时回滚）
     */
    void multithreadingTransactionTest();
}
