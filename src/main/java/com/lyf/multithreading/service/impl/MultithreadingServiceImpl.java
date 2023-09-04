package com.lyf.multithreading.service.impl;

import com.lyf.multithreading.model.to.createthread.CreateThreadFirst;
import com.lyf.multithreading.model.to.createthread.CreateThreadSecond;
import com.lyf.multithreading.model.to.createthread.CreateThreadThird;
import com.lyf.multithreading.service.MultithreadingService;
import com.lyf.multithreading.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Future;
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

    @Resource
    ThreadPoolTaskExecutor taskExecutor;

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
//        FutureTask<Boolean> futureTask = new FutureTask<>(() -> {
//            log.info("CreateThreadThird ===================> 进入线程run方法内");
//            sysUserService.addTest("创建线程方法3", 3);
//            log.info("CreateThreadThird ===================> 线程run方法内执行完毕");
//            return true;
//        });
//        FutureTask<Boolean> futureTask = new FutureTask<>(createThreadThird);
//        Thread thread = new Thread(futureTask);
//        thread.start();
        Future<Boolean> futureTask = taskExecutor.submit(createThreadThird);
        Boolean result = null;
        try {
            result = futureTask.get();
        } catch (Exception e) {
            log.error("创建线程方法3 ===================> 出现异常：{}", e.getMessage());
        }
        log.info("创建线程方法3 ===================> 执行方法完毕，返回结果为：{}", result);
    }

    @Override
    public void threadPoolTest() {
        log.info("线程池测试 ===================> 开始执行方法");
        // Executors 一共有4个初始化方法（阿里巴巴不推荐使用）
        // newFixedThreadPool() 创建一个固定大小的线程池
        // newSingleThreadExecutor() 创建一个单线程化的线程池
        // newCachedThreadPool() 创建一个可缓存的线程池
        // newScheduledThreadPool() 创建一个定长线程池，支持定时及周期性任务执行
        // 以上方法的不同之处：
        // newCacheThreadPool()方法，执行速度快，一下子创建2的31次方-1个线程，缺点是CPU负荷太大。核心线程数为0，非核心线程数为最大值（2的31次方-1），连接时间为60s，SynchronousQueue为队列，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间。与其他队列不同的是，它维护一组线程，这些线程在等待着把元素加入或移出队列。
        // newFixedThreadPool()方法，执行速度慢，缺点是可能会内存溢出。核心线程数为0，非核心线程数自定义，连接时间为0，用的LinkedBlockingQueue队列，为链表队列。
        // newSingleThreadExecutor()方法，执行速队最慢，因为他只有一个非核心线程来执行任务，如果任务数量比较多，就只能用这一个线程来处理。
        // 以上三个方法都在底层调用了ThreadPoolExecutor这个类，所以参数可以自由调配。但是阿里巴巴不允许直接使用这些方法。

        // ScheduledExecutorService 属于ExecutorService的子接口，它能够实现定时执行或周期性执行任务的功能。
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        // 也可以使用ThreadPoolExecutor类来创建线程池


        log.info("线程池测试 ===================> 执行方法完毕");
    }
}
