package com.lyf.multithreading.service.impl;

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
}
