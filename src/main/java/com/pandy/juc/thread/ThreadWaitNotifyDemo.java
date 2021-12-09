package com.pandy.juc.thread;

/**
 * @author: Pandy
 * @create: 2021/12/9
 **/
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        AirCondition airCondition = new AirCondition();
        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                airCondition.increment();
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                airCondition.decrement();
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                airCondition.increment();
            }
        }, "线程C").start();

        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                airCondition.decrement();
            }
        }, "线程D").start();
    }
}

class AirCondition {
    private int number = 0;
    public synchronized void increment() {
        while (number != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + ":" + number);
        this.notifyAll();
    }

    public synchronized void decrement() {
        while (number == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName()+":"+number);
        this.notifyAll();
    }
}
