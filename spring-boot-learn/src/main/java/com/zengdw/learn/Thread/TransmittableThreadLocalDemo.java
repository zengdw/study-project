package com.zengdw.learn.Thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author : zengd
 * @date : Created in 2021/2/2 10:49
 * @description: 线程池模式下使用ThreadLocal https://www.cnblogs.com/hama1993/p/10409740.html
 * @version:
 */
public class TransmittableThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
        ThreadLocal<Integer> local = new TransmittableThreadLocal<>();
        executor= TtlExecutors.getTtlExecutor(executor);
        local.set(1);
        executor.execute(() -> {
            System.out.println("in " + local.get());
        });
        Thread.sleep(5000);
        local.set(12);
        executor.execute(() -> {
            System.out.println("in2 " + local.get());
        });
    }
}
