package com.pandy.juc.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Pandy
 * @create: 2021/12/9
 **/
public class ThreadWaitNotifyDemo2 {
    public static void main(String[] args) {
        AirConditionTwo airCondition = new AirConditionTwo();
        new Thread(()->{ for (int i = 0; i <10 ; i++) airCondition.decrement();},"线程A").start();
        new Thread(()->{ for (int i = 0; i <10 ; i++) airCondition.increment();},"线程B").start();
        new Thread(()->{ for (int i = 0; i <10 ; i++) airCondition.decrement();},"线程C").start();
        new Thread(()->{ for (int i = 0; i <10 ; i++) airCondition.increment();},"线程D").start();
    }
}

class AirConditionTwo {
    private int number = 0;
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
