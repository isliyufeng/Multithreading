package com.lyf.multithreading.service.impl;

import com.lyf.multithreading.model.to.createthread.CreateThreadFirst;
import com.lyf.multithreading.model.to.createthread.CreateThreadSecond;
import com.lyf.multithreading.model.to.createthread.CreateThreadThird;
import com.lyf.multithreading.service.MultithreadingService;
import com.lyf.multithreading.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @describe
 * @Author joyous
 * @Date 2023/5/23
 */
@Slf4j
@Service
public class MultithreadingServiceImpl implements MultithreadingService {
    @Resource
    SysUserService sysUserService;

    @Resource
    DataSourceTransactionManager transactionManager;

    @Resource
    TransactionDefinition transactionDefinition;

    @Resource
    CreateThreadFirst createThreadFirst;

    @Resource
    CreateThreadSecond createThreadSecond;

    @Resource
    CreateThreadThird createThreadThird;

    @Override
    public void multithreadingTransactionTest() {
        int size = 5;
        CyclicBarrier barrier = new CyclicBarrier(size);
        // 创建Boolean类型的原子类
        AtomicReference<Boolean> rollback = new AtomicReference<>(false);

        for (int i = 1; i <= size; i++) {
            int currentNum = i;

            new Thread(() -> {
                // 手动开启事务
                TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
                try {
                    // 新增用户
                    if (!sysUserService.addTest("李钰锋", currentNum)) {
                        log.error("手动异常");
                        throw new RuntimeException("线程" + currentNum + "出现异常");
                    }
                } catch (Exception e) {
                    rollback.set(true);
                }

                try {
                    // 等待所有线程执行完毕
                    log.info("============= 线程进入等待，线程 {} =============", currentNum);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

                log.info("线程 {} 执行完毕", currentNum);
                // 判断是否回滚
                if (rollback.get()) {
                    // 手动回滚
                    transactionManager.rollback(transaction);
                    log.info("线程 {} 回滚", currentNum);
                    return;
                }
                //
                transactionManager.commit(transaction);
            }).start();
        }
    }

    @Override
    public void createThreadMethod1() {
        log.info("创建线程方法1 ===================> 开始执行方法");
        createThreadFirst.start();
        // 也可以直接使用匿名内部类
//        new Thread(() -> {
//            log.info("创建线程方法1 ===================> 进入线程run方法内");
//            sysUserService.addTest("创建线程方法1", 2);
//            log.info("创建线程方法1 ===================> 线程run方法内执行完毕");
//        }).start();
        log.info("创建线程方法1 ===================> 执行方法完毕");
    }

    @Override
    public void createThreadMethod2() {
        log.info("创建线程方法2 ===================> 开始执行方法");
        new Thread(createThreadSecond).start();
        // 也可以直接使用匿名内部类
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                log.info("CreateThreadSecond ===================> 进入线程run方法内");
//                sysUserService.addTest("创建线程方法2", 2);
//                log.info("CreateThreadSecond ===================> 线程run方法内执行完毕");
//            }
//        }).start();
        log.info("创建线程方法2 ===================> 执行方法完毕");
    }

    @Override
    public void createThreadMethod3() {
        log.info("创建线程方法3 ===================> 开始执行方法");
//        FutureTask<Boolean> futureTask = new FutureTask<>(createThreadThird);
//        Thread thread = new Thread(futureTask);
//        thread.start();
//        try {
//            log.info("CreateThreadThird ===================> 线程执行结果为：{}", futureTask.get());
//        } catch (Exception e) {
//            log.error("CreateThreadThird ===================> 线程执行异常：{}", e.getMessage());
//        }

        // 也可以直接使用匿名内部类
        FutureTask<Boolean> futureTask = new FutureTask<>(() -> {
            log.info("CreateThreadThird ===================> 进入线程run方法内");
            sysUserService.addTest("创建线程方法3", 3);
            log.info("CreateThreadThird ===================> 线程run方法内执行完毕");
            return true;
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        log.info("创建线程方法3 ===================> 执行方法完毕");
    }

    @Override
    public void threadPoolTest() {
        log.info("线程池测试 ===================> 开始执行方法");
//        Executors.newScheduledThreadPool()
        log.info("线程池测试 ===================> 执行方法完毕");
    }
}
