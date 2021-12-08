package com.pandy.juc;

import java.util.concurrent.TimeUnit;

/**
 * maven有监控线程 gradle没有吗？
 * 打印出的线程数量为1
 */
public class ThreadActiveCount {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().getThreadGroup().list();
        System.out.println(Thread.activeCount());

        // 可读性更好 底层还是Thread.sleep()
        TimeUnit.SECONDS.sleep(1);
        // TimeUnit.MINUTES.sleep(1);
        // TimeUnit.DAYS.sleep(1);
    }
}
