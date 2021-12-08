package com.pandy.juc.thread;

/**
 * @author: Pandy
 * @create: 2021/12/7
 **/
public class ThreadDemo {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("我是主线程" + i);
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("我是线程" + i);
        }
    }
}