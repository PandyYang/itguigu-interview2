package com.pandy.juc.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: Pandy
 * @create: 2021/12/7
 **/
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "coming...");
            return 1024;
        });
        new Thread(futureTask).start();
        // 如果future task放在main线程前面，会导致main线程阻塞
        // Object o = futureTask.get();
        // System.out.println(Thread.currentThread().getName()+"\t"+"线程来了...");

        while (true) {
            if (futureTask.isDone()) {
                System.out.println("使用轮询来解决，值为" + futureTask.get());
                break;
            } else {
                System.out.println("阻塞中");
            }
        }
    }
}
