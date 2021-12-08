package com.pandy.juc.thread;

/**
 * @author: Pandy
 * @create: 2021/12/7
 **/
public class RunnableDemo {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
        for (int i = 0; i < 100; i++) {
            System.out.println("我是主线程" + i);
        }
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("我是线程" + i);
        }
    }
}
