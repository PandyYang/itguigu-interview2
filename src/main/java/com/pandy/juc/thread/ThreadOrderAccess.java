package com.pandy.juc.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Pandy
 * @create: 2021/12/9
 * 多个线程之间按照顺序调用，实现A -> B -> C
 * 三个线程启动，要求如下：
 * AA打印5次，BB打印10次， CC打印15次
 * 重复10轮
 **/
public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                shareResource.print5();
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                shareResource.print10();
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                shareResource.print15();
            }
        }, "线程C").start();
    }
}

class ShareResource {
    private int number = 1;
    private final Lock lock = new ReentrantLock();
    final Condition condition1 = lock.newCondition();
    final Condition condition2 = lock.newCondition();
    final Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1){
                condition1.await();
            }

            for (int i = 0; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }
            for (int i = 0; i < 11; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            condition3.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            for (int i = 0; i < 16; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}