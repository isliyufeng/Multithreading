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

    /**
     * 创建线程方法1
     */
    void createThreadMethod1();

    /**
     * 创建线程方法2
     */
    void createThreadMethod2();

    /**
     * 创建线程方法3
     */
    void createThreadMethod3();

    /**
     * 线程池的应用
     */
    void threadPoolTest();
}
